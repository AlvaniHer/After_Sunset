package com.example.aftersunset.ui.screens.event

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.EventRepository
import com.example.aftersunset.domain.model.Event
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class EventDetailViewModel : ViewModel() {
    private val repository = EventRepository()

    var event by mutableStateOf<Event?>(null)
    var isLoading by mutableStateOf(false)

    fun loadEvent(id: String) {
        viewModelScope.launch {
            isLoading = true
            event = repository.getEventById(id)
            isLoading = false
        }
    }
}