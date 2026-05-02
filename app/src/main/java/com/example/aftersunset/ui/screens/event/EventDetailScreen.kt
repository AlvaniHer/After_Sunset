package com.example.aftersunset.ui.screens.event

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.ui.components.event.EventContent

//TODO: Documentar la funcionalidad de esta pantalla y meter texto en strings
@Composable
fun EventDetailScreen(
    eventId: String,
    onBackClick: () -> Unit,
    onBuyClick: (String, String, Double) -> Unit
) {
    val event = sampleEvents.find { it.id == eventId }
    if (event == null)
        Text(text = "Evento no encontrado") // TODO: Hacer una pantalla de error para este caso
    else {
        EventContent(
            event = event,
            onBackClick = onBackClick,
            onBuyClick = {
                onBuyClick(event.id, "Entrada General", event.price)
            }
        )
    }
}
