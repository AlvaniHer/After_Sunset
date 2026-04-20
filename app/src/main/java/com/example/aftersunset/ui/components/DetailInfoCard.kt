package com.example.aftersunset.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.PacificCyan

//TODO: Documentar la funcionalidad de este componente
@Composable
fun DetailInfoCard(event: Event) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.05f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(Icons.Default.DateRange, event.date)
                InfoItem(Icons.Default.Favorite, event.clubName)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                InfoItem(Icons.Default.LocationOn, event.fullAddress)
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = PacificCyan, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
fun DetailInfoCardPreview(){
    AfterSunsetTheme {
        DetailInfoCard(
            Event(
                id = "1",
                title = "Neon Ritual",
                clubName = "Sala Gold",
                date = "Viernes, 24 Mayo",
                price = 15.0,
                imageUrl = "https://picsum.photos/id/123/800/600",
                genre = "Techno / Melodic",
                tags = listOf("Centro", "VIP", "Luces LED"),
                zone = "Málaga Centro",
                fullAddress = "C. Luis de Velázquez, 5, 29008 Málaga",
                latitude = 36.7218,
                longitude = -4.4185,
                description = "Vive la experiencia techno más exclusiva en el corazón de Málaga. Sonido Funktion-One y el mejor ambiente."
            )
        )
    }
}