package com.example.aftersunset.ui.components.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.R
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Representa el estado vacío de la pantalla de tickets.
 * Se muestra cuando el usuario no tiene ninguna entrada comprada, 
 * animándole a explorar eventos en el mapa.
 */
@Composable
fun EmptyTicketsState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_confirmation_number),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.1f),
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Aún no tienes planes...",
            color = Color.White.copy(alpha = 0.5f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            "¡Busca tu próxima fiesta en el mapa!",
            color = PacificCyan,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun EmptyTicketsStatePreview() {
    AfterSunsetTheme {
        EmptyTicketsState()
    }
}
