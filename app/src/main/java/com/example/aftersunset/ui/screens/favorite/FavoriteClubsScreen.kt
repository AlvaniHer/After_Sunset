package com.example.aftersunset.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.aftersunset.domain.model.FavoriteClub
import com.example.aftersunset.navigation.VenueProfile
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FavoriteClubsScreen(
    navController: NavController
) {
    var favoriteClubs by remember { mutableStateOf<List<FavoriteClub>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        if (uid == null) {
            favoriteClubs = emptyList()
            isLoading = false
            return@LaunchedEffect
        }

        db.collection("favoritos_clubes")
            .whereEqualTo("id_usuario", uid)
            .get()
            .addOnSuccessListener { result ->
                favoriteClubs = result.documents.map { doc ->
                    FavoriteClub(
                        id = doc.id,
                        userId = doc.getString("id_usuario") ?: "",
                        venueId = doc.getString("id_local") ?: "",
                        clubName = doc.getString("nombre_local") ?: "",
                        address = doc.getString("direccion") ?: "",
                        imageUrl = doc.getString("imagenUrl") ?: ""
                    )
                }
                isLoading = false
            }
            .addOnFailureListener {
                favoriteClubs = emptyList()
                isLoading = false
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "CLUBES FAVORITOS",
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 8.dp,
                    start = 8.dp
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )

            Text(
                text = "Tus salas guardadas para próximas noches",
                modifier = Modifier.padding(
                    start = 8.dp,
                    bottom = 24.dp
                ),
                color = Color.White.copy(alpha = 0.55f),
                style = MaterialTheme.typography.bodyMedium
            )

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                favoriteClubs.isEmpty() -> {
                    EmptyFavoriteClubsState()
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(favoriteClubs) { club ->
                            FavoriteClubItem(
                                club = club,
                                onClick = {
                                    navController.navigate(VenueProfile(club.venueId))
                                }
                            )
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 48.dp, start = 16.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .align(Alignment.TopStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun FavoriteClubItem(
    club: FavoriteClub,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171321)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = club.imageUrl,
                contentDescription = club.clubName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Black.copy(alpha = 0.15f),
                            0.55f to Color.Black.copy(alpha = 0.45f),
                            1.0f to Color.Black.copy(alpha = 0.85f)
                        )
                    )
            )

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorito",
                tint = Dragonfruit,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                    .padding(8.dp)
                    .size(22.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp)
            ) {
                Text(
                    text = club.clubName,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PacificCyan,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = club.address,
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoriteClubsState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Dragonfruit,
                modifier = Modifier.size(52.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Aún no tienes clubes favoritos",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pulsa el corazón en una sala o evento para guardarla aquí.",
                color = Color.White.copy(alpha = 0.55f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}