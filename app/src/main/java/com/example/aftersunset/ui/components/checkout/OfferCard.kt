package com.example.aftersunset.ui.components.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Offer
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun OfferCard(
    offer: Offer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) PacificCyan.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.05f),
        border = BorderStroke(2.dp, if (isSelected) PacificCyan else Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = PacificCyan,
                    unselectedColor = Color.White.copy(alpha = 0.6f)
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = offer.titulo,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${offer.precio}€",
                    color = PacificCyan,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (offer.descripcion.isNotEmpty()) {
                    Text(
                        text = offer.descripcion,
                        color = Color.White.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}