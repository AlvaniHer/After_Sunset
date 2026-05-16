package com.example.aftersunset.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileSettingsViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var user by mutableStateOf<User?>(null)
        private set

    var username by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var isLoading by mutableStateOf(true)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val uid = auth.currentUser?.uid ?: throw Exception("No hay usuario conectado")

                val snapshot = db.collection("usuarios")
                    .document(uid)
                    .get()
                    .await()

                val loadedUser = snapshot.toObject(User::class.java)

                user = loadedUser
                username = loadedUser?.username.orEmpty()
            } catch (e: Exception) {
                errorMessage = "No se ha podido cargar tu perfil."
            }

            isLoading = false
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            val uid = currentUser?.uid

            errorMessage = null
            successMessage = null

            if (currentUser == null || uid == null) {
                errorMessage = "No hay ningún usuario conectado."
                return@launch
            }

            val cleanUsername = username.trim()

            if (cleanUsername.isBlank()) {
                errorMessage = "El nombre de usuario no puede estar vacío."
                return@launch
            }

            if (newPassword.isNotBlank()) {
                if (newPassword.length < 6) {
                    errorMessage = "La contraseña debe tener al menos 6 caracteres."
                    return@launch
                }

                if (newPassword != confirmPassword) {
                    errorMessage = "Las contraseñas no coinciden."
                    return@launch
                }
            }

            isSaving = true

            try {
                val currentUsername = user?.username.orEmpty()

                if (cleanUsername != currentUsername) {
                    val usernameQuery = db.collection("usuarios")
                        .whereEqualTo("username", cleanUsername)
                        .get()
                        .await()

                    val usernameUsedByOtherUser = usernameQuery.documents.any { document ->
                        document.id != uid
                    }

                    if (usernameUsedByOtherUser) {
                        errorMessage = "Ese nombre de usuario ya está en uso."
                        isSaving = false
                        return@launch
                    }
                }

                db.collection("usuarios")
                    .document(uid)
                    .update("username", cleanUsername)
                    .await()

                if (newPassword.isNotBlank()) {
                    currentUser.updatePassword(newPassword).await()
                }

                user = user?.copy(username = cleanUsername)
                username = cleanUsername
                newPassword = ""
                confirmPassword = ""
                successMessage = "Perfil actualizado correctamente."
            } catch (e: FirebaseAuthRecentLoginRequiredException) {
                errorMessage = "Para cambiar la contraseña debes cerrar sesión, volver a iniciar sesión e intentarlo de nuevo."
            } catch (e: Exception) {
                errorMessage = "No se han podido guardar los cambios."
            }

            isSaving = false
        }
    }
}
