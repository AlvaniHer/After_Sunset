package com.example.aftersunset.ui.screens.venue

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.domain.model.Venue
import com.example.aftersunset.domain.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VenueViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    var venue by mutableStateOf<Venue?>(null)
    var localEvents by mutableStateOf<List<Event>>(emptyList())
    var reviews by mutableStateOf<List<Review>>(emptyList())
    var isLoading by mutableStateOf(true)

    fun loadVenueData(venueId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val venueDoc = db.collection("locales").document(venueId).get().await()
                venue = venueDoc.toObject(Venue::class.java)?.copy(id = venueDoc.id)

                val eventsSnapshot = db.collection("eventos")
                    .whereEqualTo("venueId", venueId)
                    .get().await()

                localEvents = eventsSnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Event::class.java)?.copy(id = doc.id)
                }

                val reviewsSnapshot = db.collection("resenas")
                    .whereEqualTo("venueId", venueId)
                    .get().await()

                val reviewsList = reviewsSnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Review::class.java)?.copy(id = doc.id)
                }

                reviewsList.forEach { review ->
                    if (review.userId.isNotEmpty()) {
                        try {
                            val userDoc = db.collection("usuarios").document(review.userId).get().await()
                            if (userDoc.exists()) {

                                review.user = userDoc.getString("nombre") ?: "Usuario Anónimo"
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("VenueVM", "Error buscando usuario ${review.userId}: ${e.message}")
                        }
                    }
                }

                reviews = reviewsList

            } catch (e: Exception) {
                android.util.Log.e("VenueVM", "Error cargando datos del local: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}