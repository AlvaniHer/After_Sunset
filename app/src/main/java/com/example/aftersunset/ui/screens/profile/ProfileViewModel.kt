package com.example.aftersunset.ui.screens.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import com.example.aftersunset.domain.model.User
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    var user by mutableStateOf<User?>(null)
    var isLoading by mutableStateOf(true)

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            isLoading = true
            user = authRepository.getCurrentUserProfile()
            isLoading = false
        }
    }
}