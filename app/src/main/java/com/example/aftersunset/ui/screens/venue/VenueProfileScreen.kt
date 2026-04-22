package com.example.aftersunset.ui.screens.venue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.domain.model.Local
import com.example.aftersunset.ui.components.home.EventCard
import com.example.aftersunset.ui.components.venue.VenueStatChip
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Pantalla de perfil detallado de un local (Venue).
 * Muestra información técnica del establecimiento, próximos eventos, reseñas y opción de favoritos.
 *
 * @param venueId Identificador único del local.
 * @param onBackClick Callback para regresar a la pantalla anterior.
 * @param onEventClick Callback para navegar al detalle de un evento del local.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueProfileScreen(
    venueId: String,
    onBackClick: () -> Unit,
    onEventClick: (String) -> Unit
) {
    val venue = remember(venueId) {
        Local(
            id = venueId.toIntOrNull() ?: 1,
            nombre = if (venueId == "1") "SALA GOLD" else "MOLIERE PLAYA",
            zona = "Málaga Centro",
            direccion = "C. Luis de Velázquez, 5, 29008 Málaga",
            aforo = 500,
            edadMinima = 21,
            fotoPrincipal = "https://picsum.photos/id/123/800/600",
            descripcion = "El templo del ocio nocturno en el corazón de Málaga. Sonido Funktion-One, iluminación LED de última generación y los mejores reservados de la ciudad."
        )
    }

    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = InkBlack
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            item {
                Box(modifier = Modifier.height(300.dp).fillMaxWidth()) {
                    AsyncImage(
                        model = venue.fotoPrincipal,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Transparent,
                                    0.7f to Color.Transparent,
                                    1f to InkBlack
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                        IconButton(
                            onClick = { isFavorite = !isFavorite },
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = if (isFavorite) Dragonfruit else Color.White
                            )
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = venue.nombre.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = PacificCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(venue.zona, color = PacificCyan, style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("4.8 (120 reseñas)", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelMedium)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        VenueStatChip("AFORO", venue.aforo.toString())
                        VenueStatChip("EDAD", "+${venue.edadMinima}")
                        VenueStatChip("TIPO", "Club")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("SOBRE EL LOCAL", color = PacificCyan, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = venue.descripcion,
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "PRÓXIMOS EVENTOS",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            val localEvents = SampleData.sampleEvents.filter { it.clubName.contains(venue.nombre, ignoreCase = true) }
            items(localEvents) { event ->
                Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                    EventCard(
                        event = event,
                        onClick = { onEventClick(event.id) }
                    )
                }
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
