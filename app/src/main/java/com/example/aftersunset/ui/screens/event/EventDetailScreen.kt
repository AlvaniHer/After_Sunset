package com.example.aftersunset.ui.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.ui.components.event.EventContent
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Pantalla de detalle de un evento.
 * Recupera la información del evento seleccionado y la delega al componente visual [EventContent].
 *
 * @param eventId Identificador único del evento que se desea visualizar.
 * @param onBackClick Callback para gestionar la navegación hacia atrás.
 * @param onVenueClick Callback para navegar al perfil del local/empresa.
 * @param onBuyClick Callback que inicia el flujo de compra de entradas.
 * @param onLocationClick Callback para navegar al mapa centrado en el local del evento.
 */
@Composable
fun EventDetailScreen(
    eventId: String,
    onBackClick: () -> Unit,
    onVenueClick: (String) -> Unit,
    onBuyClick: (String, String, Double) -> Unit,
    onLocationClick: (Double, Double) -> Unit,
    viewModel: EventDetailViewModel = viewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    val event = viewModel.event

    Box(modifier = Modifier.fillMaxSize().background(InkBlack)) {
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PacificCyan
            )
        } else if (event != null) {
            EventContent(
                event = event,
                isFavorite = viewModel.isFavorite,
                onFavoriteClick = { viewModel.toggleFavorite() },
                onBackClick = onBackClick,
                onVenueClick = { onVenueClick(event.venueId) },
                onBuyClick = { onBuyClick(event.id, "Entrada General", event.price) },
                onLocationClick = { onLocationClick(event.latitude, event.longitude) }
            )
        } else {
            Text(
                text = "Evento no encontrado",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}