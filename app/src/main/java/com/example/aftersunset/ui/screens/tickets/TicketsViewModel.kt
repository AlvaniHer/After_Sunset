package com.example.aftersunset.ui.screens.tickets

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftersunset.data.repository.AuthRepository
import com.example.aftersunset.domain.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TicketsViewModel : ViewModel() {
    /**
     *  Clase de ticket + datos del evento (latitud y longitud)
     */
    data class TicketWithDetails(
        val ticket: Ticket,
        val latitude: Double? = null,
        val longitude: Double? = null,
        val venueId: String? = null
    )
    var ticketsWithDetails by mutableStateOf<List<TicketWithDetails>>(emptyList())
    private val db = FirebaseFirestore.getInstance()
    private val authRepository = AuthRepository()

    var tickets by mutableStateOf<List<Ticket>>(emptyList())
    var isLoading by mutableStateOf(false)

    fun loadUserTickets() {
        viewModelScope.launch {
            isLoading = true
            try {
                val user = authRepository.getCurrentUserProfile()
                if (user != null && user.id.isNotEmpty()) {
                    val ticketSnapshot = db.collection("entradas")
                        .whereEqualTo("id_usuario", user.id)
                        .get().await()

                    val tempTickets = ticketSnapshot.documents.mapNotNull { doc ->
                        doc.toObject(Ticket::class.java)?.copy(id = doc.id)
                    }

                    ticketsWithDetails = tempTickets.map { ticket ->
                        async {
                            try {
                                val eventDoc = db.collection("eventos")
                                    .document(ticket.eventId)
                                    .get().await()

                                if (eventDoc.exists()) {
                                    TicketWithDetails(
                                        ticket = ticket,
                                        latitude = eventDoc.getDouble("latitud") ?: 0.0,
                                        longitude = eventDoc.getDouble("longitud") ?: 0.0,
                                        venueId = eventDoc.getString("id_local")
                                    )
                                } else {
                                    TicketWithDetails(ticket = ticket)
                                }
                            } catch (e: Exception) {
                                TicketWithDetails(ticket = ticket)
                            }
                        }.await()

                    }
                }
            } catch (e: Exception) {
                Log.e("TicketsVM", "Error fatal: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}