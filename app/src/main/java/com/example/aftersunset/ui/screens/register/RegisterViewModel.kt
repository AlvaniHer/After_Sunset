package com.example.aftersunset.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import com.example.aftersunset.domain.model.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository()


    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var isUsernameValid by mutableStateOf(true)
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
        private set
    var location by mutableStateOf("Málaga, ES")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    /**
     * Creacion del username unico al usuario escibir el name y el surname
     */
    fun onNameChange(newName: String) {
        name = newName
        val words = newName.trim().split(" ")
        if (words.size >= 2) {
            val firstName = words.first().lowercase()
            val lastName = words.last().lowercase()

            val generated = "$firstName.$lastName${(10..99).random()}"
            username = generated
            checkUsername(generated)
        }
    }
    /**
     * Funcion por si el usuario decide editar el username
     */
    fun onUsernameChange(newUsername: String) {
        username = newUsername.lowercase().replace(" ", "")
        checkUsername(username)
    }

    private fun checkUsername(nameToCheck: String) {
        viewModelScope.launch {
            if (nameToCheck.length > 3) {
                isUsernameValid = repository.isUsernameAvailable(nameToCheck)
            }
        }
    }

    /**
     * Funcion para validar la contraseña escrita
     */
    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword = newValue
    }
    fun isFormValid(): Boolean {
        return password.isNotEmpty() &&
                password == confirmPassword &&
                password.length >= 6
    }

    /**
     * Ejecuta el proceso de registro.
     * @param onSuccess Callback que se ejecuta si el registro es exitoso.
     */
    fun onRegisterClick(onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || password.isBlank() || username.isBlank()) {
            errorMessage = "Por favor, rellena todos los campos"
            return
        }
        if (!isUsernameValid) {
            errorMessage = "El nombre de usuario no está disponible"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val newUser = User(
                name = name,
                username = username,
                email = email,
                location = location
            )

            val result = repository.registerUser(newUser, password)

            result.fold(
                onSuccess = {
                    isLoading = false
                    onSuccess()
                },
                onFailure = { error ->
                    isLoading = false
                    errorMessage = error.message ?: "Error desconocido al registrarse"
                }
            )
        }
    }
}