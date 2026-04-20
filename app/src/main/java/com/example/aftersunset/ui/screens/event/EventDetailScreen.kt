package com.example.aftersunset.ui.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.DetailInfoCard
import com.example.aftersunset.ui.components.SunsetActionButton
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

//TODO: Documentar la funcionalidad de esta pantalla y meter texto en strings
@Composable
fun EventDetailScreen(
    eventId: String,
    onBackClick: () -> Unit
) {
    val event = Event(
        id = "1",
        title = "Neon Ritual",
        clubName = "Sala Gold",
        date = "Viernes, 24 Mayo",
        price = 15.0,
        imageUrl = "https://picsum.photos/id/123/800/600",
        genre = "Techno / Melodic",
        tags = listOf("Centro", "VIP", "Luces LED"),
        zone = "Málaga Centro",
        fullAddress = "C. Luis de Velázquez, 5, 29008 Málaga",
        latitude = 36.7218,
        longitude = -4.4185,
        description = "Vive la experiencia techno más exclusiva en el corazón de Málaga. Sonido Funktion-One y el mejor ambiente."
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.height(450.dp).fillMaxWidth()) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.0f to Color.Transparent,
                                0.6f to Color.Transparent,
                                1.0f to InkBlack
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .offset(y = (-40).dp)
            ) {
                Text(
                    text = event.genre.uppercase(),
                    color = PacificCyan,
                    style = MaterialTheme.typography.labelMedium,
                    letterSpacing = 2.sp
                )
                Text(
                    text = event.title,
                    color = Color.White,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailInfoCard(event)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sobre este evento",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Prepárate para una noche inolvidable en ${event.clubName}. El mejor ambiente de la ciudad con un despliegue visual sin precedentes. No te quedes sin tu entrada para el evento más esperado de la temporada.",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        SunsetActionButton(
            text = "COMPRAR ENTRADA - ${event.price}€",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .navigationBarsPadding()
            ,
            onClick = {} // Navegar a pagar
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 48.dp, start = 16.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
        }
    }
}