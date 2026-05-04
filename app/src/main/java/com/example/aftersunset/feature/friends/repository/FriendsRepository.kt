package com.example.aftersunset.feature.friends.repository

import com.example.aftersunset.feature.friends.model.FriendUser
import com.example.aftersunset.feature.friends.model.Friendship
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
            .get()
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

                val user = document.toObject(FriendUser::class.java)
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

        friendshipRef.get()
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
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        onError(exception)
                    }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun getPendingRequests(
        onResult: (List<Pair<String, Friendship>>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val currentUserId = getCurrentUserId()

        if (currentUserId == null) {
            onError(IllegalStateException("No hay usuario autenticado"))
            return
        }

        db.collection("amistades")
            .whereEqualTo("id_usuario_receptor", currentUserId)
            .whereEqualTo("estado_amistad", "pendiente")
            .get()
            .addOnSuccessListener { result ->
                val requests = result.documents.mapNotNull { document ->
                    val friendship = document.toObject(Friendship::class.java)
                    if (friendship != null) {
                        document.id to friendship
                    } else {
                        null
                    }
                }
                onResult(requests)
            }
            .addOnFailureListener { exception ->
                onError(exception)
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
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun getAcceptedFriendships(
        onResult: (List<Pair<String, Friendship>>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val currentUserId = getCurrentUserId()

        if (currentUserId == null) {
            onError(IllegalStateException("No hay usuario autenticado"))
            return
        }

        db.collection("amistades")
            .whereEqualTo("estado_amistad", "aceptada")
            .get()
            .addOnSuccessListener { result ->
                val friendships = result.documents.mapNotNull { document ->
                    val friendship = document.toObject(Friendship::class.java)
                    if (
                        friendship != null &&
                        (friendship.id_usuario_emisor == currentUserId ||
                                friendship.id_usuario_receptor == currentUserId)
                    ) {
                        document.id to friendship
                    } else {
                        null
                    }
                }
                onResult(friendships)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun getUserById(
        userId: String,
        onResult: (Pair<String, FriendUser>?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("usuarios")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(FriendUser::class.java)
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
}