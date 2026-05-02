package com.example.aftersunset.ui.components.checkout

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PacificCyan
import kotlin.random.Random

@Composable
fun NeonParticlesEffect() {
    val particles = remember { List(30) { ParticleData() } }
    val infiniteTransition = rememberInfiniteTransition(label = "Particles")

    particles.forEach { particle ->
        val xOffset by infiniteTransition.animateFloat(
            initialValue = particle.startX,
            targetValue = particle.endX,
            animationSpec = infiniteRepeatable(
                animation = tween(particle.duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "x"
        )
        val yOffset by infiniteTransition.animateFloat(
            initialValue = particle.startY,
            targetValue = particle.endY,
            animationSpec = infiniteRepeatable(
                animation = tween(particle.duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "y"
        )
        val alpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(particle.duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "alpha"
        )

        Box(
            modifier = Modifier
                .offset(x = xOffset.dp, y = yOffset.dp)
                .size(particle.size.dp)
                .alpha(alpha)
                .background(particle.color, CircleShape)
        )
    }
}

class ParticleData {
    val startX = Random.nextInt(-200, 200).toFloat()
    val startY = Random.nextInt(-300, 300).toFloat()
    val endX = startX + Random.nextInt(-100, 100)
    val endY = startY - Random.nextInt(200, 400)
    val duration = Random.nextInt(1500, 3000)
    val size = Random.nextInt(2, 6)
    val color = if (Random.nextBoolean()) PacificCyan else Dragonfruit
}
