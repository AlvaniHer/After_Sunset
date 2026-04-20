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
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Event
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
fun QuickStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.5f),
            style = MaterialTheme.typography.labelSmall
        )
    }
}