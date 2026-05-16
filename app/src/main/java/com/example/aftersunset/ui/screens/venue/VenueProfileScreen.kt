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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.ui.components.home.EventCard
import com.example.aftersunset.ui.components.venue.*
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun VenueProfileScreen(
    venueId: String,
    onBackClick: () -> Unit,
    onEventClick: (String) -> Unit,
    onLocationClick: (Double, Double) -> Unit,
    onSeeAllReviewsClick: (String, String) -> Unit,
    viewModel: VenueViewModel = viewModel()
) {
    LaunchedEffect(venueId) {
        viewModel.loadVenueData(venueId)
    }

    val venue = viewModel.venue
    var isFollowing by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = InkBlack
    ) { innerPadding ->
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PacificCyan)
            }
        } else if (venue != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                item {
                    VenueHeader(
                        mainPhoto = venue.mainPhoto,
                        isFavorite = viewModel.isFavorite,
                        onBackClick = onBackClick,
                        onToggleFavorite = { viewModel.toggleFavorite() }
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
                                Text(
                                    venue.zone,
                                    color = PacificCyan,
                                    style = MaterialTheme.typography.labelMedium
                                )
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
                        Text(
                            "SOBRE EL LOCAL",
                            color = PacificCyan,
                            style = MaterialTheme.typography.labelLarge
                        )
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
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Spacer(modifier = Modifier.height(48.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "RESEÑAS",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black
                            )

                            TextButton(onClick = {
                                onSeeAllReviewsClick(venue.id, venue.name)
                            }) {
                                Text("VER TODAS", color = PacificCyan)
                            }
                        }

                        VenueReviewsSection(reviews = viewModel.reviews)
                    }
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

                items(viewModel.localEvents) { event ->
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
}