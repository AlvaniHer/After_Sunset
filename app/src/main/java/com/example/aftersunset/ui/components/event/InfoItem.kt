package com.example.aftersunset.ui.components.event

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Representa un elemento de información básica con un icono y un texto.
 * Se utiliza para mostrar detalles como la fecha o el nombre del club.
 *
 * @param icon Icono descriptivo de la información.
 * @param text Texto informativo a mostrar.
 * @param modifier Modificador opcional para personalizar el comportamiento o apariencia.
 */
@Composable
fun InfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = PacificCyan, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}
