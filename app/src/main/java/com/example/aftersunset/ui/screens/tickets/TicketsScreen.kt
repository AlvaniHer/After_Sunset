package com.example.aftersunset.ui.screens.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.navigation.Maps
import com.example.aftersunset.navigation.VenueProfile
import com.example.aftersunset.ui.components.tickets.EmptyTicketsState
import com.example.aftersunset.ui.components.tickets.TicketItem
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

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
    navController: NavController,
    viewModel: TicketsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadUserTickets()
    }
    val ticketsUI = viewModel.ticketsWithDetails
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "MIS ENTRADAS",
            modifier = Modifier.padding(top = 64.dp, bottom = 24.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PacificCyan)
            }
        }else if (ticketsUI.isEmpty()) {
            EmptyTicketsState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(ticketsUI) { item ->
                    TicketItem(
                        ticket = item.ticket,
                        onLocationClick = {
                            navController.navigate(Maps(lat = item.latitude, lng = item.longitude)) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        onVenueClick = {
                            item.venueId?.let { id -> navController.navigate(VenueProfile(id)) }
                        }
                    )
                }
            }
        }
    }
}
