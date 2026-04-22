package com.example.aftersunset.ui.components.event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Tarjeta que agrupa la información logística principal de un evento.
 * Incluye la fecha, el nombre del club y la dirección completa.
 *
 * @param event Objeto [Event] que contiene los datos a mostrar.
 */
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

@Preview
@Composable
fun DetailInfoCardPreview(){
    AfterSunsetTheme {
        DetailInfoCard(
            event = sampleEvents[0]
        )
    }
}
