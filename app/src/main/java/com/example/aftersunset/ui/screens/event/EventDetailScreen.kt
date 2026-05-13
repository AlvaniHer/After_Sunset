package com.example.aftersunset.ui.screens.event

import androidx.compose.runtime.Composable
import com.example.aftersunset.ui.components.event.EventContent

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
    onLocationClick: (Double, Double) -> Unit
) {
    EventContent(
        eventId = eventId,
        onBackClick = onBackClick,
        onVenueClick = { venueId -> onVenueClick(venueId) },
        onBuyClick = { event ->
            onBuyClick(event.id, "General", event.price)
        },
        onLocationClick = { event ->
            onLocationClick(event.latitude, event.longitude)
        }
    )
}
