package com.example.aftersunset.ui.screens.event

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.ui.components.event.EventContent
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EventDetailScreen(
    eventId: String,
    onBackClick: () -> Unit,
    onVenueClick: (String) -> Unit,
    onBuyClick: (String, String, Double) -> Unit,
    onLocationClick: (Double, Double) -> Unit
) {
    val event = sampleEvents.find { it.id == eventId }

    if (event == null) {
        Text(text = "Evento no encontrado")
        return
    }

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var isFavorite by remember { mutableStateOf(false) }

    val favoriteId = "${uid}_${event.venueId}"

    LaunchedEffect(uid, event.venueId) {
        if (uid != null) {
            db.collection("favoritos_clubes")
                .document(favoriteId)
                .get()
                .addOnSuccessListener { document ->
                    isFavorite = document.exists()
                }
        }
    }

    EventContent(
        event = event,
        isFavorite = isFavorite,
        onFavoriteClick = {
            if (uid == null) return@EventContent

            val favoriteRef = db.collection("favoritos_clubes")
                .document(favoriteId)

            if (isFavorite) {
                favoriteRef.delete()
                    .addOnSuccessListener {
                        isFavorite = false
                    }
            } else {
                val favoriteClub = hashMapOf(
                    "id_usuario" to uid,
                    "id_local" to event.venueId,
                    "nombre_local" to event.clubName,
                    "direccion" to event.fullAddress,
                    "imagenUrl" to event.imageUrl,
                    "fecha_agregado" to Timestamp.now()
                )

                favoriteRef.set(favoriteClub)
                    .addOnSuccessListener {
                        isFavorite = true
                    }
            }
        },
        onBackClick = onBackClick,
        onVenueClick = { onVenueClick(event.venueId) },
        onBuyClick = {
            onBuyClick(event.id, "Entrada General", event.price)
        },
        onLocationClick = {
            onLocationClick(event.latitude, event.longitude)
        }
    )
}