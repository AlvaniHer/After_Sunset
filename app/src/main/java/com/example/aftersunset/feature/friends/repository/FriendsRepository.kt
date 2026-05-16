package com.example.aftersunset.feature.friends.repository

import com.example.aftersunset.feature.friends.model.FriendUser
import com.example.aftersunset.feature.friends.model.Friendship
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source

class FriendsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun searchUserByUsername(
        username: String,
        onResult: (Pair<String, FriendUser>?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val currentUserId = getCurrentUserId()

        db.collection("usuarios")
            .whereEqualTo("username", username)
            .get(Source.SERVER)
            .addOnSuccessListener { result ->
                val document = result.documents.firstOrNull()

                if (document == null) {
                    onResult(null)
                    return@addOnSuccessListener
                }

                if (document.id == currentUserId) {
                    onResult(null)
                    return@addOnSuccessListener
                }

                val user = document.toFriendUserOrNull()
                if (user != null) {
                    onResult(document.id to user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun sendFriendRequest(
        receiverUserId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val senderUserId = getCurrentUserId()

        if (senderUserId == null) {
            onError(IllegalStateException("No hay usuario autenticado"))
            return
        }

        if (senderUserId == receiverUserId) {
            onError(IllegalArgumentException("No puedes agregarte a ti mismo"))
            return
        }

        val friendshipId = buildFriendshipId(senderUserId, receiverUserId)
        val friendshipRef = db.collection("amistades").document(friendshipId)

        friendshipRef.get(Source.SERVER)
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    onError(IllegalStateException("Ya existe una solicitud o amistad entre estos usuarios"))
                    return@addOnSuccessListener
                }

                val friendship = Friendship(
                    id_usuario_emisor = senderUserId,
                    id_usuario_receptor = receiverUserId,
                    estado_amistad = "pendiente",
                    fecha_solicitud = Timestamp.now(),
                    fecha_respuesta = null
                )

                friendshipRef.set(friendship)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { exception -> onError(exception) }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun observePendingRequests(
        currentUserId: String,
        onResult: (List<Pair<String, Friendship>>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {
        return db.collection("amistades")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                val requests = snapshot?.documents.orEmpty().mapNotNull { document ->
                    val friendship = document.toFriendshipOrNull()

                    if (
                        friendship != null &&
                        friendship.id_usuario_receptor == currentUserId &&
                        friendship.estado_amistad.equals("pendiente", ignoreCase = true)
                    ) {
                        document.id to friendship
                    } else {
                        null
                    }
                }

                onResult(requests)
            }
    }

    fun observeAcceptedFriendships(
        currentUserId: String,
        onResult: (List<Pair<String, Friendship>>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {
        return db.collection("amistades")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                val uniqueFriendIds = mutableSetOf<String>()

                val friendships = snapshot?.documents.orEmpty().mapNotNull { document ->
                    val friendship = document.toFriendshipOrNull() ?: return@mapNotNull null

                    val isAccepted = friendship.estado_amistad.equals("aceptada", ignoreCase = true)
                    val belongsToCurrentUser =
                        friendship.id_usuario_emisor == currentUserId ||
                                friendship.id_usuario_receptor == currentUserId

                    if (!isAccepted || !belongsToCurrentUser) {
                        return@mapNotNull null
                    }

                    val otherUserId = if (friendship.id_usuario_emisor == currentUserId) {
                        friendship.id_usuario_receptor
                    } else {
                        friendship.id_usuario_emisor
                    }

                    if (uniqueFriendIds.contains(otherUserId)) {
                        null
                    } else {
                        uniqueFriendIds.add(otherUserId)
                        document.id to friendship
                    }
                }

                onResult(friendships)
            }
    }

    fun acceptFriendRequest(
        friendshipId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("amistades")
            .document(friendshipId)
            .update(
                mapOf(
                    "estado_amistad" to "aceptada",
                    "fecha_respuesta" to Timestamp.now()
                )
            )
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    }

    fun getUserById(
        userId: String,
        onResult: (Pair<String, FriendUser>?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("usuarios")
            .document(userId)
            .get(Source.SERVER)
            .addOnSuccessListener { document ->
                val user = document.toFriendUserOrNull()
                if (user != null) {
                    onResult(document.id to user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    private fun buildFriendshipId(userId1: String, userId2: String): String {
        return listOf(userId1, userId2).sorted().joinToString("_")
    }

    private fun DocumentSnapshot.toFriendshipOrNull(): Friendship? {
        val emisor = getString("id_usuario_emisor") ?: return null
        val receptor = getString("id_usuario_receptor") ?: return null
        val estado = getString("estado_amistad") ?: return null
        val fechaSolicitud = getTimestamp("fecha_solicitud")
        val fechaRespuesta = getTimestamp("fecha_respuesta")

        return Friendship(
            id_usuario_emisor = emisor,
            id_usuario_receptor = receptor,
            estado_amistad = estado,
            fecha_solicitud = fechaSolicitud,
            fecha_respuesta = fechaRespuesta
        )
    }

    private fun DocumentSnapshot.toFriendUserOrNull(): FriendUser? {
        val email = getString("email") ?: ""
        val username = getString("username") ?: ""
        val nombre = getString("nombre") ?: ""
        val apellidos = getString("apellidos") ?: ""
        val fotoPerfil = getString("foto_perfil") ?: ""
        val estadoCuenta = getString("estado_cuenta") ?: ""

        val id = getString("id") ?: this.id
        val name = getString("name") ?: ""
        val profileImageUrl = getString("profileImageUrl") ?: ""
        val location = getString("location") ?: ""
        val level = getString("level") ?: ""
        val pendingLevelUp = getBoolean("pendingLevelUp") ?: false

        val points = (getLong("points") ?: 0L).toInt()
        val followingCount = (getLong("followingCount") ?: 0L).toInt()
        val eventsAttended = (getLong("eventsAttended") ?: 0L).toInt()

        return FriendUser(
            nombre = nombre,
            apellidos = apellidos,
            email = email,
            username = username,
            foto_perfil = fotoPerfil,
            estado_cuenta = estadoCuenta,
            fecha_nacimiento = getTimestamp("fecha_nacimiento"),
            fecha_registro = getTimestamp("fecha_registro"),
            id = id,
            name = name,
            profileImageUrl = profileImageUrl,
            location = location,
            level = level,
            points = points,
            followingCount = followingCount,
            eventsAttended = eventsAttended,
            pendingLevelUp = pendingLevelUp
        )
    }
}