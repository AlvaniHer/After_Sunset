package com.example.aftersunset.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.home.EventCard
import com.example.aftersunset.ui.components.home.HomeHeader
import com.example.aftersunset.ui.theme.AfterSunsetTheme

//TODO: Meter texto en strings.xml
/**
 * Pantalla principal de exploración de eventos.
 * @param onEventClick Función para navegar al detalle del evento seleccionado.
 */
@Composable
fun HomeScreen(onEventClick: (String) -> Unit) {
    val dummyEvents = listOf(
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
            description = "Vive la experiencia techno más exclusiva en el corazón de Málaga. Sonido Funktion-One y el mejor ambiente.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
        Event(
            id = "2",
            title = "Sunset Beats",
            clubName = "Moliere Playa",
            date = "Sábado, 25 Mayo",
            price = 20.0,
            imageUrl = "https://picsum.photos/id/158/800/600",
            genre = "House / Nu-Disco",
            tags = listOf("Beach Club", "Vistas al Mar", "Terraza"),
            zone = "Torremolinos / Los Álamos",
            fullAddress = "Paseo Marítimo Los Álamos, s/n, 29620 Torremolinos",
            latitude = 36.6436,
            longitude = -4.4674,
            description = "Baila bajo las estrellas frente al Mediterráneo. La fiesta comienza al atardecer y no para hasta el amanecer.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
        Event(
            id = "3",
            title = "Urban Jungle",
            clubName = "The Sound",
            date = "Jueves, 23 Mayo",
            price = 10.0,
            imageUrl = "https://picsum.photos/id/249/800/600",
            genre = "Reggaeton / Urbano",
            tags = listOf("Universitario", "Barra Libre", "Teatinos"),
            zone = "Teatinos",
            fullAddress = "C. Plutarco, 58, 29010 Málaga",
            latitude = 36.7165,
            longitude = -4.4712,
            description = "La noche de los jueves en Teatinos tiene un nombre. Música urbana y los mejores cócteles de la zona.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AfterSunsetTheme.colors.background)
            .padding(horizontal = 20.dp)
    ) {
        item {
            HomeHeader()
        }

        item {
            Text(
                text = "PRÓXIMOS EVENTOS",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 16.dp),
                letterSpacing = 2.sp
            )
        }

        items(dummyEvents) { event ->
            EventCard(
                event = event,
                onClick = { onEventClick(event.id) }
            )
        }
    }
}