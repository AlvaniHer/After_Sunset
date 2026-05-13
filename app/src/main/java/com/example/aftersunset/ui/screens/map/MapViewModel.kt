package com.example.aftersunset.ui.screens.map

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.data.repository.EventRepository
import kotlinx.coroutines.launch
class MapViewModel : ViewModel() {

    // 1. Instanciamos el repositorio en lugar de la DB directa
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
                // 2. Usamos el repositorio que ya trae los datos del local (lat/lng)
                val fetchedEvents = repository.getAllEvents()

                events = fetchedEvents

                Log.d("MapVM", "Eventos cargados y enriquecidos: ${events.size}")
                // Log extra para verificar que las coordenadas no son 0.0
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
