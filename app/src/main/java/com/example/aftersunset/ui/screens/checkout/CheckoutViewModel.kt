package com.example.aftersunset.ui.screens.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import com.example.aftersunset.data.repository.EventRepository
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.domain.model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.aftersunset.domain.model.UserLevel

class CheckoutViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    private val authRepository = AuthRepository()
    private val db = FirebaseFirestore.getInstance()

    var event by mutableStateOf<Event?>(null)
    var isProcessing by mutableStateOf(false)
    var purchaseSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var eventOffers by mutableStateOf<List<Offer>>(emptyList())


    /**
     * Carga inicial de datos del evento y sus ofertas vinculadas.
     */
    fun loadCheckoutData(eventId: String) {
        viewModelScope.launch {
            isProcessing = true
            errorMessage = null
            try {
                val result = eventRepository.getEventById(eventId)
                if (result != null) {
                    event = result
                } else {
                    errorMessage = "No se encontró el evento con ID: $eventId"
                }

                val snapshot = db.collection("ofertas")
                    .whereEqualTo("id_evento", eventId)
                    .get()
                    .await()

                eventOffers = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Offer::class.java)?.copy(id = doc.id)
                }
            }catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                android.util.Log.e("CheckoutVM", "Error cargando datos", e)
            } finally {
                isProcessing = false
            }
        }
    }
    /**
     * Procesa la compra, genera el ticket y actualiza puntos/nivel.
     */
    fun processPurchase(eventId: String, ticketType: String, price: Double) {
        viewModelScope.launch {
            isProcessing = true
            errorMessage = null

            try {
                val user = authRepository.getCurrentUserProfile()
                val currentEvent = event ?: eventRepository.getEventById(eventId)

                if (user != null && currentEvent != null) {
                    val uniqueQrData = "AS-${user.id.take(4)}-${eventId.take(4)}-${System.currentTimeMillis()}" //QR

                    val updatedPoints = user.points + 50
                    val updatedEvents = user.eventsAttended + 1
                    val newLevel = when {
                        updatedPoints >= 1000 -> UserLevel.LEGENDARY
                        updatedPoints >= 500 -> UserLevel.GOLD
                        updatedPoints >= 200 -> UserLevel.VIP
                        else -> UserLevel.STANDARD
                    }

                    val ticketData = hashMapOf(
                        "id_usuario" to user.id,
                        "id_evento" to eventId,
                        "imageUrl" to currentEvent.imageUrl,
                        "titulo_evento" to currentEvent.title,
                        "nombre_local" to currentEvent.clubName,
                        "precio_total" to (price + 1.5),
                        "tipo_entrada" to ticketType,
                        "codigo_qr" to uniqueQrData,
                        "fecha_compra" to com.google.firebase.Timestamp.now(),
                        "estado_entrada" to "pagada"
                    )
                    db.collection("entradas").add(ticketData).await()

                    db.collection("usuarios").document(user.id).update(
                        "points", updatedPoints,
                        "eventsAttended", updatedEvents,
                        "level", newLevel.name
                    ).await()

                    purchaseSuccess = true
                }else {
                    errorMessage = "No se pudo encontrar la información del usuario o del evento."
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Ocurrió un error inesperado durante el pago."
                android.util.Log.e("CheckoutVM", "Error en la compra: ", e)
            } finally {
                isProcessing = false
            }
        }
    }

}