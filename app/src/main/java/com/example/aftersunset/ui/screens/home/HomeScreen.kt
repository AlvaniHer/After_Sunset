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
import com.example.aftersunset.data.SampleData.sampleEvents
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

        items(sampleEvents) { event ->
            EventCard(
                event = event,
                onClick = { onEventClick(event.id) }
            )
        }
    }
}