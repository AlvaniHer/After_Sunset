package com.example.aftersunset.ui.components.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Diálogo que se muestra tras una acción exitosa (compra o subida de nivel).
 * Gestiona la estructura del [Dialog], las animaciones de escala y el efecto de brillo neón.
 *
 * @param onDismiss Acción a ejecutar cuando se cierra el diálogo.
 * @param title Título principal del mensaje.
 * @param message Cuerpo descriptivo del mensaje.
 * @param isLevelUp Si es true, activa la estética especial de "Level Up" con partículas y mayor brillo.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    isLevelUp: Boolean = false
) {
    var show by remember { mutableStateOf(true) }
    val context = LocalContext.current

    DialogVibrationEffect(context, isLevelUp)

    val scale by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "DialogScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "GlowTransition")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    if (show) {
        Dialog(
            onDismissRequest = { show = false; onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLevelUp) {
                    NeonParticlesEffect()
                }

                Surface(
                    modifier = Modifier
                        .padding(32.dp)
                        .scale(scale)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = InkBlack.copy(alpha = 0.95f),
                    border = BorderStroke(
                        width = 2.dp,
                        brush = if (isLevelUp) 
                            Brush.linearGradient(listOf(PacificCyan, Dragonfruit))
                        else 
                            Brush.linearGradient(listOf(PacificCyan.copy(0.5f), PacificCyan.copy(0.1f)))
                    )
                ) {
                    SuccessDialogContent(
                        title = title,
                        message = message,
                        isLevelUp = isLevelUp,
                        glowAlpha = glowAlpha,
                        onConfirm = { show = false; onDismiss() }
                    )
                }
            }
        }
    }
}
