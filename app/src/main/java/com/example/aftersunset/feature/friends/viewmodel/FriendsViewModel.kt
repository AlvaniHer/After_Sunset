package com.example.aftersunset.feature.friends.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aftersunset.feature.friends.model.FriendUser
import com.example.aftersunset.feature.friends.model.Friendship
import com.example.aftersunset.feature.friends.repository.FriendsRepository
import com.google.firebase.firestore.ListenerRegistration

class FriendsViewModel(
    private val repository: FriendsRepository = FriendsRepository()
) : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    var foundUser by mutableStateOf<Pair<String, FriendUser>?>(null)
        private set

    var pendingRequests by mutableStateOf<List<Pair<String, Friendship>>>(emptyList())
        private set

    var acceptedFriendships by mutableStateOf<List<Pair<String, Friendship>>>(emptyList())
        private set

    var friendUsers by mutableStateOf<Map<String, FriendUser>>(emptyMap())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var message by mutableStateOf<String?>(null)
        private set

    private var pendingListener: ListenerRegistration? = null
    private var acceptedListener: ListenerRegistration? = null
    private var observedUserId: String? = null

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }

    fun onSearchTextChange(value: String) {
        searchText = value
    }

    fun clearMessage() {
        message = null
    }

    fun startObserving() {
        val currentUserId = repository.getCurrentUserId() ?: return

        if (observedUserId == currentUserId && pendingListener != null && acceptedListener != null) {
            return
        }

        stopObserving()
        resetFriendsState()
        observedUserId = currentUserId

        pendingListener = repository.observePendingRequests(
            currentUserId = currentUserId,
            onResult = { requests ->
                pendingRequests = requests
                preloadUsersFromPendingRequests(requests)
            },
            onError = { exception ->
                message = exception.message ?: "Error al cargar solicitudes"
            }
        )

        acceptedListener = repository.observeAcceptedFriendships(
            currentUserId = currentUserId,
            onResult = { friendships ->
                acceptedFriendships = friendships
                preloadUsersFromAcceptedFriendships(friendships)
            },
            onError = { exception ->
                message = exception.message ?: "Error al cargar amigos"
            }
        )
    }

    fun stopObserving() {
        pendingListener?.remove()
        acceptedListener?.remove()
        pendingListener = null
        acceptedListener = null
        observedUserId = null
    }

    fun resetFriendsState() {
        foundUser = null
        pendingRequests = emptyList()
        acceptedFriendships = emptyList()
        friendUsers = emptyMap()
        isLoading = false
        message = null
    }

    fun searchUser() {
        val username = searchText.trim()

        if (username.isEmpty()) {
            message = "Introduce un username"
            foundUser = null
            return
        }

        isLoading = true

        repository.searchUserByUsername(
            username = username,
            onResult = { result ->
                foundUser = result
                isLoading = false
                if (result == null) {
                    message = "No se ha encontrado ningún usuario"
                }
            },
            onError = { exception ->
                isLoading = false
                foundUser = null
                message = exception.message ?: "Error al buscar usuario"
            }
        )
    }

    fun sendFriendRequest() {
        val receiverUserId = foundUser?.first

        if (receiverUserId == null) {
            message = "No hay usuario seleccionado"
            return
        }

        isLoading = true

        repository.sendFriendRequest(
            receiverUserId = receiverUserId,
            onSuccess = {
                isLoading = false
                message = "Solicitud enviada correctamente"
                foundUser = null
                searchText = ""
            },
            onError = { exception ->
                isLoading = false
                message = exception.message ?: "Error al enviar solicitud"
            }
        )
    }

    fun acceptFriendRequest(friendshipId: String) {
        isLoading = true

        repository.acceptFriendRequest(
            friendshipId = friendshipId,
            onSuccess = {
                isLoading = false
                message = "Solicitud aceptada"
            },
            onError = { exception ->
                isLoading = false
                message = exception.message ?: "Error al aceptar solicitud"
            }
        )
    }

    private fun preloadUsersFromPendingRequests(
        requests: List<Pair<String, Friendship>>
    ) {
        val currentUserId = repository.getCurrentUserId() ?: return

        requests.forEach { (_, friendship) ->
            val otherUserId = if (friendship.id_usuario_emisor == currentUserId) {
                friendship.id_usuario_receptor
            } else {
                friendship.id_usuario_emisor
            }

            if (!friendUsers.containsKey(otherUserId)) {
                repository.getUserById(
                    userId = otherUserId,
                    onResult = { result ->
                        if (result != null) {
                            friendUsers = friendUsers + (result.first to result.second)
                        }
                    },
                    onError = {}
                )
            }
        }
    }

    private fun preloadUsersFromAcceptedFriendships(
        friendships: List<Pair<String, Friendship>>
    ) {
        val currentUserId = repository.getCurrentUserId() ?: return

        friendships.forEach { (_, friendship) ->
            val otherUserId = if (friendship.id_usuario_emisor == currentUserId) {
                friendship.id_usuario_receptor
            } else {
                friendship.id_usuario_emisor
            }

            if (!friendUsers.containsKey(otherUserId)) {
                repository.getUserById(
                    userId = otherUserId,
                    onResult = { result ->
                        if (result != null) {
                            friendUsers = friendUsers + (result.first to result.second)
                        }
                    },
                    onError = {}
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopObserving()
    }
}