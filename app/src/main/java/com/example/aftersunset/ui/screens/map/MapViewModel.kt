package com.example.aftersunset.ui.screens.map

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.data.repository.EventRepository
import kotlinx.coroutines.launch
class MapViewModel : ViewModel() {
    private val repository = EventRepository()

    var events by mutableStateOf<List<Event>>(emptyList())
    var isLoading by mutableStateOf(false)

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            isLoading = true
            try {
                val fetchedEvents = repository.getAllEvents()

                events = fetchedEvents

                Log.d("MapVM", "Eventos cargados y enriquecidos: ${events.size}")
                events.forEach {
                    Log.d("MapVM", "Evento: ${it.title} -> Lat: ${it.latitude}, Lng: ${it.longitude}")
                }

            } catch (e: Exception) {
                Log.e("MapVM", "Error al cargar eventos a través del repositorio", e)
            } finally {
                isLoading = false
            }
        }
    }
}
