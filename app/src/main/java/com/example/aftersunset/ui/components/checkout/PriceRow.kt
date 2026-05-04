package com.example.aftersunset.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
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
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.Dragonfruit

/**
 * Representa una fila de desglose de precios en el resumen de compra.
 * Permite diferenciar visualmente entre conceptos individuales y el importe total.
 *
 * @param label Descripción del concepto.
 * @param value Valor monetario formateado.
 * @param isTotal Si es true, aplica un estilo destacado con mayor tamaño y peso de fuente.
 */
@Composable
fun PriceRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = if (isTotal) 8.dp else 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium,
            color = if (isTotal) Color.White else Color.White.copy(alpha = 0.6f),
            fontWeight = if (isTotal) FontWeight.Black else FontWeight.Normal
        )
        Text(
            text = value,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium,
            color = if (isTotal) Dragonfruit else Color.White,
            fontWeight = if (isTotal) FontWeight.Black else FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PriceRowPreview(){
    AfterSunsetTheme {
        PriceRow(
            label = "Precio",
            value = "10€",
            isTotal = true
        )
    }
}
