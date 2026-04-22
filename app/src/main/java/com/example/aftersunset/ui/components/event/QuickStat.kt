package com.example.aftersunset.ui.components.event

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Muestra una métrica rápida del evento.
 * Diseñado para ser utilizado dentro de una fila de estadísticas.
 *
 * @param label Título de la estadística.
 * @param value Valor de la estadística.
 */
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

@Composable
@Preview
fun QuickStatPreview() {
    AfterSunsetTheme {
        QuickStat(
            label = "Aforo",
            value = "500"
        )
    }
}
