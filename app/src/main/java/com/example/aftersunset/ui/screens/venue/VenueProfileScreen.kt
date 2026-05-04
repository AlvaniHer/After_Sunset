package com.example.aftersunset.ui.screens.venue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.ui.components.home.EventCard
import com.example.aftersunset.ui.components.venue.*
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Pantalla de perfil detallado de un local (Venue).
 * Orquesta los diferentes componentes modulares para mostrar la ficha técnica, eventos y reseñas.
 *
 * @param venueId Identificador único del local.
 * @param onBackClick Callback para regresar a la pantalla anterior.
 * @param onEventClick Callback para navegar al detalle de un evento del local.
 * @param onLocationClick Callback para navegar al mapa centrado en este local.
 */
@Composable
fun VenueProfileScreen(
    venueId: String,
    onBackClick: () -> Unit,
    onEventClick: (String) -> Unit,
    onLocationClick: (Double, Double) -> Unit
) {

    val venue = remember(venueId) {
        SampleData.sampleVenues.find { it.id.toString() == venueId } ?: SampleData.sampleVenues.first()
    }

    var isFavorite by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = InkBlack
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            item {
                VenueHeader(
                    mainPhoto = venue.mainPhoto,
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onToggleFavorite = { isFavorite = !isFavorite }
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = venue.name.uppercase(),
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Text(venue.zone, color = PacificCyan, style = MaterialTheme.typography.labelMedium)
                        }
                        
                        Button(
                            onClick = { isFollowing = !isFollowing },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowing) Color.White.copy(alpha = 0.1f) else Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(40.dp)
                        ) {
                            Text(
                                text = if (isFollowing) "SIGUIENDO" else "SEGUIR",
                                color = if (isFollowing) Color.White else Color.Black,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            item {
                VenueInfoSection(
                    address = venue.address,
                    capacity = venue.capacity,
                    minAge = venue.minAge,
                    onLocationClick = { onLocationClick(venue.latitude, venue.longitude) }
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                    Text("SOBRE EL LOCAL", color = PacificCyan, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = venue.description,
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 12.dp),
                        lineHeight = 24.sp
                    )
                }
            }

            item { VenueGallerySection() }

            item { 
                Spacer(modifier = Modifier.height(48.dp))
                VenueReviewsSection() 
            }

            item {
                Text(
                    text = "PRÓXIMOS EVENTOS",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }

            val localEvents = SampleData.sampleEvents.filter { it.venueId == venue.id.toString() }
            items(localEvents) { event ->
                Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                    EventCard(
                        event = event,
                        onClick = { onEventClick(event.id) }
                    )
                }
            }
            
            item { Spacer(modifier = Modifier.height(50.dp)) }
        }
    }
}
