package com.example.aftersunset.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import com.example.aftersunset.data.repository.EventRepository
import com.example.aftersunset.domain.model.Event
import kotlinx.coroutines.launch

/**
 * Gestor de estado para la pantalla de inicio.
 * Se encarga de pedir los datos al repositorio y exponerlos a la UI.
 */
class HomeViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    private val authRepository = AuthRepository()

    var events by mutableStateOf<List<Event>>(emptyList())
    var userName by mutableStateOf("Cargando...")
    var isLoading by mutableStateOf(true)

    init {
        fetchHomeData()
    }


    /**
     * Llama al repositorio de forma asíncrona para no bloquear la app
     * mientras se descargan los datos de Firebase.
     */
    private fun fetchHomeData() {
        viewModelScope.launch {
            isLoading = true
            events = eventRepository.getAllEvents()

            val user = authRepository.getCurrentUserProfile()
            userName = user?.name ?: "Explorador"

            isLoading = false
        }
    }
}