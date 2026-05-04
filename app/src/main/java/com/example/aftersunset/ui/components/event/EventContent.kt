package com.example.aftersunset.ui.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun EventContent(
    event: Event,
    onBackClick: () -> Unit,
    onVenueClick: () -> Unit,
    onBuyClick: () -> Unit,
    onLocationClick: () -> Unit
) {
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

                Spacer(modifier = Modifier.height(32.dp))

                Text("Información", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = event.description,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                InfoItem(Icons.Default.Info, "Para mayores de ${event.minAge} años (necesario traer DNI).")
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(Icons.Default.Notifications, "Organizado por ${event.clubName}")
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(Icons.Default.Refresh, "Puedes obtener un reembolso si es dentro de las 24 horas posteriores a la compra.")

                HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp), color = Color.White.copy(alpha = 0.1f))

                Text("Sala", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelLarge)
                Text(event.clubName, color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(event.fullAddress, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodyMedium)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onLocationClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Default.LocationOn, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ABRIR EN EL MAPA", color = Color.White, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(Icons.Default.Info, "Apertura de puertas: 23:59")

                HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp), color = Color.White.copy(alpha = 0.1f))

                Text("Promotor", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(56.dp).clickable { onVenueClick() },
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.1f)
                    ) {
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = event.clubName,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f).clickable { onVenueClick() }
                    )
                    Button(
                        onClick = { /* Lógica de seguir */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("SEGUIR", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(150.dp))
            }
        }

        SunsetActionButton(
            text = "COMPRAR ENTRADA - ${event.price}€",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .navigationBarsPadding()
            ,
            onClick = onBuyClick
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

@Preview
@Composable
fun EventContentPreview(){
    AfterSunsetTheme {
        EventContent(
            event = sampleEvents[0],
            onBackClick = {},
            onVenueClick = {},
            onBuyClick = {},
            onLocationClick = {}
        )
    }
}
