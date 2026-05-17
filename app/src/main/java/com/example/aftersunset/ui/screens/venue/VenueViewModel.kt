package com.example.aftersunset.ui.screens.venue

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.domain.model.Venue
import com.example.aftersunset.domain.model.Review
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VenueViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    var venue by mutableStateOf<Venue?>(null)
    var localEvents by mutableStateOf<List<Event>>(emptyList())
    var reviews by mutableStateOf<List<Review>>(emptyList())
    var isLoading by mutableStateOf(true)
    var isFavorite by mutableStateOf(false)

    fun loadVenueData(venueId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val venueDoc = db.collection("locales").document(venueId).get().await()
                val loadedVenue = venueDoc.toObject(Venue::class.java)?.copy(id = venueDoc.id)
                venue = loadedVenue

                loadedVenue?.let {
                    checkIfFavorite(it.id)
                }

                val eventsSnapshot = db.collection("eventos")
                    .whereEqualTo("id_local", venueId)
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
                                val username = userDoc.getString("username")?.split(" ")
                                    ?.firstOrNull()
                                    ?: "Usuario"
                                review.user = if (username != null) "$username" else "Usuario Anónimo"
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

    private fun checkIfFavorite(venueId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val favoriteId = "${uid}_${venueId}"

        db.collection("favoritos_clubes")
            .document(favoriteId)
            .addSnapshotListener { snapshot, _ ->
                isFavorite = snapshot?.exists() == true
            }
    }

    fun toggleFavorite() {
        val currentVenue = venue ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val favoriteId = "${uid}_${currentVenue.id}"
        val favoriteRef = db.collection("favoritos_clubes").document(favoriteId)

        if (isFavorite) {
            favoriteRef.delete()
        } else {
            val favoriteClub = hashMapOf(
                "id_usuario" to uid,
                "id_local" to currentVenue.id,
                "nombre_local" to currentVenue.name,
                "direccion" to currentVenue.address,
                "imagenUrl" to currentVenue.mainPhoto,
                "fecha_agregado" to Timestamp.now()
            )
            favoriteRef.set(favoriteClub)
        }
    }
}