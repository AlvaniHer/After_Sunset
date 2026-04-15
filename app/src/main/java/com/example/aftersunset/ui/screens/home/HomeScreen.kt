package com.example.aftersunset.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.EventCard
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Pantalla principal de exploración de eventos.
 * * @param onEventClick Callback para navegar al detalle del evento seleccionado.
 */
@Composable
fun HomeScreen(onEventClick: (String) -> Unit) {
    val dummyEvents = listOf(
        Event(
            "1",
            "Neon Ritual",
            "Fabrik",
            "12 Mayo",
            25.0,
            "https://media.timeout.com/images/103639448/image.jpg",
            "Madrid",
            "Techno"
        ),
        Event("2", "Sunset Beats", "Opium", "15 Mayo", 30.0, "https://youbarcelona.com/uploads/images/c/opium%20barcelona%20gente%2020/original.jpg ", "Barcelona", "House"),
        Event("3", "Urban Night", "Shoko", "20 Mayo", 20.0, "https://fiestaismadrid.es/wp-content/uploads/2023/02/Shoko-Madrid-Fiesta-is-Madrid-1a.jpg", "Madrid", "Reggaeton")
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

/**
 * Cabecera con el logo y el botón de perfil.
 */
@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "AFTER",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                color = Color.White,
                lineHeight = 24.sp
            )
            Text(
                text = "SUNSET",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    brush = AfterSunsetTheme.gradients.actionGradient,
                    lineHeight = 32.sp
                )
            )
        }

        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.1f),
            border = BorderStroke(1.dp, AfterSunsetTheme.gradients.borderGradient)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}