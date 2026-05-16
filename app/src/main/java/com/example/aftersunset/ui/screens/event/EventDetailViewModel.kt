package com.example.aftersunset.ui.screens.event

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.EventRepository
import com.example.aftersunset.domain.model.Event
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class EventDetailViewModel : ViewModel() {
    private val repository = EventRepository()
    private val db = FirebaseFirestore.getInstance()

    var event by mutableStateOf<Event?>(null)
    var isLoading by mutableStateOf(false)
    var isFavorite by mutableStateOf(false)

    fun loadEvent(id: String) {
        viewModelScope.launch {
            isLoading = true
            val loadedEvent = repository.getEventById(id)
            event = loadedEvent
            isLoading = false

            loadedEvent?.let {
                checkIfFavorite(it.venueId)
            }
        }
    }

    private fun checkIfFavorite(venueId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val favoriteId = "${uid}_${venueId}"

        db.collection("favoritos_clubes")
            .document(favoriteId)
            .get()
            .addOnSuccessListener { document ->
                isFavorite = document.exists()
            }
    }

    fun toggleFavorite() {
        val currentEvent = event ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val favoriteId = "${uid}_${currentEvent.venueId}"
        val favoriteRef = db.collection("favoritos_clubes").document(favoriteId)

        if (isFavorite) {
            favoriteRef.delete()
                .addOnSuccessListener {
                    isFavorite = false
                }
        } else {
            val favoriteClub = hashMapOf(
                "id_usuario" to uid,
                "id_local" to currentEvent.venueId,
                "nombre_local" to currentEvent.clubName,
                "direccion" to currentEvent.fullAddress,
                "imagenUrl" to currentEvent.imageUrl,
                "fecha_agregado" to Timestamp.now()
            )

            favoriteRef.set(favoriteClub)
                .addOnSuccessListener {
                    isFavorite = true
                }
        }
    }
}