package com.example.aftersunset.ui.components.checkout

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

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

    LaunchedEffect(Unit) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        vibrator?.let { v ->
            if (v.hasVibrator()) {
                if (isLevelUp) {
                    v.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 80, 40, 80), -1))
                } else {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }
        }
    }

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
                modifier = Modifier
                    .fillMaxSize(),
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
                    border = androidx.compose.foundation.BorderStroke(
                        width = 2.dp,
                        brush = if (isLevelUp) 
                            Brush.linearGradient(listOf(PacificCyan, Dragonfruit))
                        else 
                            Brush.linearGradient(listOf(PacificCyan.copy(0.5f), PacificCyan.copy(0.1f)))
                    )
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
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            letterSpacing = 1.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 24.sp
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Button(
                            onClick = { show = false; onDismiss() },
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
            }
        }
    }
}
