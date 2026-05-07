package com.example.aftersunset.ui.screens.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.navigation.Maps
import com.example.aftersunset.navigation.VenueProfile
import com.example.aftersunset.ui.components.tickets.EmptyTicketsState
import com.example.aftersunset.ui.components.tickets.TicketItem
import com.example.aftersunset.ui.theme.InkBlack
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Pantalla de gestión de entradas adquiridas por el usuario.
 * Muestra una lista de tickets interactivos (físico-digitales) o un estado vacío
 * si no hay planes próximos. Permite la navegación geolocalizada desde cada ticket.
 *
 * @param tickets Lista de objetos [Ticket] asociados a la cuenta del usuario.
 * @param navController Controlador de navegación para permitir saltar a la pestaña de Mapas o Perfil del Local.
 */
@Composable
fun TicketsScreen(
    navController: NavController
) {
    var userTickets by remember { mutableStateOf<List<Ticket>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        if (uid == null) {
            userTickets = emptyList()
            isLoading = false
            return@LaunchedEffect
        }

        db.collection("entradas")
            .whereEqualTo("id_usuario", uid)
            .get()
            .addOnSuccessListener { result ->
                userTickets = result.documents.map { doc ->
                    Ticket(
                        id = doc.id,
                        eventId = doc.getString("id_evento") ?: "",
                        eventTitle = doc.getString("eventTitle") ?: "Entrada",
                        clubName = doc.getString("clubName") ?: "",
                        date = doc.getString("date") ?: "",
                        time = doc.getString("time") ?: "",
                        entryType = doc.getString("tipo_entrada") ?: "",
                        price = doc.getDouble("precio_pagado") ?: 0.0,
                        qrCodeData = doc.getString("codigo_qr") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                }

                isLoading = false
            }
            .addOnFailureListener {
                userTickets = emptyList()
                isLoading = false
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "MIS ENTRADAS",
            modifier = Modifier.padding(
                top = 64.dp,
                bottom = 24.dp,
                start = 8.dp
            ),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }

            userTickets.isEmpty() -> {
                EmptyTicketsState()
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(userTickets) { ticket ->
                        val relatedEvent = SampleData.sampleEvents.find {
                            it.id == ticket.eventId
                        }

                        TicketItem(
                            ticket = ticket,
                            onLocationClick = {
                                navController.navigate(
                                    Maps(
                                        lat = relatedEvent?.latitude,
                                        lng = relatedEvent?.longitude
                                    )
                                ) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            onVenueClick = {
                                relatedEvent?.let { event ->
                                    navController.navigate(
                                        VenueProfile(event.venueId)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}