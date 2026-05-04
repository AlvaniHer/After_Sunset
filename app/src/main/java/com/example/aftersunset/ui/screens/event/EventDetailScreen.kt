package com.example.aftersunset.ui.screens.event

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.aftersunset.data.SampleData.sampleEvents
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
    val event = sampleEvents.find { it.id == eventId }
    if (event == null)
        Text(text = "Evento no encontrado") // TODO: Implementar una pantalla de error visualmente acorde al tema
    else {
        EventContent(
            event = event,
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
}
