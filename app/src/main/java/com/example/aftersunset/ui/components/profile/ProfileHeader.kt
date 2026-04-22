package com.example.aftersunset.ui.components.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aftersunset.data.SampleData.sampleUser
import com.example.aftersunset.domain.model.UserLevel
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.InkBlack

/**
 * Cabecera del perfil de usuario.
 * Muestra el avatar del usuario rodeado por un anillo de gradiente neón animado
 * que cambia de color y velocidad según el nivel alcanzado.
 *
 * @param name Nombre del usuario a mostrar.
 * @param location Ubicación geográfica del usuario.
 * @param userLevel Rango actual del usuario que determina la estética del anillo y el badge.
 */
@Composable
fun ProfileHeader(name: String, location: String, userLevel: UserLevel) {
    val colors = AfterSunsetTheme.colors

    val ringColors = when (userLevel) {
        UserLevel.STANDARD -> listOf(colors.secondary, Color.DarkGray, colors.secondary)
        UserLevel.VIP -> listOf(colors.secondary, colors.primary, colors.secondary)
        UserLevel.GOLD -> listOf(colors.secondary, colors.accent1, colors.deepViolet, colors.secondary)
        UserLevel.LEGENDARY -> listOf(colors.accent1, colors.neonGold, colors.accent2, colors.accent1)
    }

    val duration = when (userLevel) {
        UserLevel.LEGENDARY -> 2000
        UserLevel.GOLD -> 4000
        else -> 6000
    }

    val infiniteTransition = rememberInfiniteTransition(label = "NightPassAnim")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .rotate(rotation)
                    .background(
                        brush = Brush.sweepGradient(colors = ringColors),
                        shape = CircleShape
                    )
            )
            
            Box(
                modifier = Modifier
                    .size(102.dp)
                    .background(colors.background, CircleShape)
            )
            
            AsyncImage(
                model = sampleUser.profileImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(2.dp, colors.background.copy(alpha = 0.5f), CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name, 
            color = Color.White, 
            style = MaterialTheme.typography.headlineMedium, 
            fontWeight = FontWeight.Black
        )

        Text(
            text = location, 
            color = Color.White.copy(alpha = 0.6f), 
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        val badgeColor = if (userLevel == UserLevel.LEGENDARY) colors.accent1 else colors.secondary
        
        Surface(
            color = badgeColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(badgeColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userLevel.name,
                    color = badgeColor,
                    style = MaterialTheme.typography.labelSmall,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileHeaderPreview(){
    AfterSunsetTheme {
        Box(modifier = Modifier.background(InkBlack).padding(20.dp)) {
            ProfileHeader(
                name = sampleUser.name,
                location = sampleUser.location,
                userLevel = sampleUser.level
            )
        }
    }
}
