package com.example.aftersunset.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Contenido visual del diálogo de éxito.
 * Muestra el icono, el título decorado, el mensaje descriptivo y el botón de acción.
 *
 * @param title Título principal.
 * @param message Mensaje descriptivo.
 * @param isLevelUp Define si se debe usar el estilo de "Level Up".
 * @param glowAlpha Nivel de opacidad para el efecto de brillo animado en el icono.
 * @param onConfirm Acción a ejecutar al pulsar el botón principal.
 */
@Composable
fun SuccessDialogContent(
    title: String,
    message: String,
    isLevelUp: Boolean,
    glowAlpha: Float,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 40.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLevelUp) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Dragonfruit.copy(alpha = glowAlpha * 0.2f), CircleShape)
                )
            }
            
            Icon(
                imageVector = if (isLevelUp) Icons.Default.Star else Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isLevelUp) Dragonfruit else PacificCyan,
                modifier = Modifier.size(72.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLevelUp) Dragonfruit else PacificCyan
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = if (isLevelUp) "¡VAMOS ALLÁ!" else "ENTENDIDO",
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}
