package com.example.aftersunset.data.repository

import kotlinx.coroutines.tasks.await
import android.util.Log
import com.example.aftersunset.domain.model.Event
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class EventRepository {
    private val db = Firebase.firestore

    suspend fun getAllEvents(): List<Event> {
        return try {
            val eventSnapshot = db.collection("eventos").get().await()
            val events = eventSnapshot.documents.mapNotNull { doc ->
                doc.toObject(Event::class.java)?.copy(id = doc.id)
            }

            events.forEach { event ->
                enrichEventWithVenueData(event)
            }
            events
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error al traer eventos: ${e.message}")
            emptyList()
        }
    }
    suspend fun getEventById(eventId: String): Event? {
        return try {
            val document = db.collection("eventos").document(eventId).get().await()
            val event = document.toObject(Event::class.java)?.copy(id = document.id)

            event?.let { enrichEventWithVenueData(it) }
            event
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error al traer evento por ID: ${e.message}")
            null
        }
    }
    /**
     * Busca los datos del local y los mete en el objeto Event.
     */
    private suspend fun enrichEventWithVenueData(event: Event) {
        if (event.venueId.isNotEmpty()) {
            try {
                val localDoc = db.collection("locales")
                    .document(event.venueId)
                    .get()
                    .await()

                if (localDoc.exists()) {
                    event.clubName = localDoc.getString("nombre") ?: ""
                    event.zone = localDoc.getString("zona") ?: ""
                    event.fullAddress = localDoc.getString("direccion") ?: ""

                    event.latitude = localDoc.getDouble("latitud") ?: 0.0
                    event.longitude = localDoc.getDouble("longitud") ?: 0.0

                    Log.d("Repository", "Evento ${event.title} enriquecido con coordenadas: ${event.latitude}")
                }
            } catch (e: Exception) {
                Log.e("Repository", "Error al enriquecer evento: ${e.message}")
            }
        }
    }
}