package com.example.aftersunset.ui.components.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PumpkinSpice

@Composable
fun EventStatsRow(event: Event) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickStat(label = "Edad", value = "+${event.minAge}")
        QuickStat(label = "Media", value = "~${event.avgAge}")
        QuickStat(label = "Aforo", value = "${event.capacity}")

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (event.isSoldOut) "AGOTADO" else "DISPONIBLE",
                color = if (event.isSoldOut) Dragonfruit else PumpkinSpice,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Estado",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
@Preview
fun EventStatsRowPreview() {
    AfterSunsetTheme {
        EventStatsRow(
            Event(
                id = "4",
                title = "Electronic Culture",
                clubName = "París 15",
                date = "Sábado, 1 Junio",
                price = 25.0,
                imageUrl = "https://picsum.photos/id/321/800/600",
                genre = "Hard Techno",
                tags = listOf("Concierto", "Aforo +3000", "Sonido Pro"),
                zone = "Polígono San Luis",
                fullAddress = "C. la Orotava, 27, 29006 Málaga",
                latitude = 36.7032,
                longitude = -4.4563,
                description = "El templo de la electrónica en el sur de España. Un despliegue de luces y sonido que te dejará sin aliento.",
                minAge = 21,
                avgAge = 26,
                capacity = 500,
                isSoldOut = false
            )
        )
    }
}