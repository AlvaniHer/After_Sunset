package com.example.aftersunset.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onLoginClick(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Introduce tus credenciales"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.loginUser(email, password)

            result.fold(
                onSuccess = {
                    isLoading = false
                    onSuccess()
                },
                onFailure = { error ->
                    isLoading = false
                    errorMessage = when {
                        error.message?.contains("password") == true -> "Contraseña incorrecta"
                        error.message?.contains("user-not-found") == true -> "El usuario no existe"
                        else -> "Error al iniciar sesión. Revisa los datos."
                    }
                }
            )
        }
    }
}