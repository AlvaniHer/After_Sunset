package com.example.aftersunset.ui.screens.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.ui.components.reviewdialog.AddReviewDialog
import com.example.aftersunset.ui.screens.venue.VenueViewModel
import com.example.aftersunset.ui.components.venue.ReviewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    venueId: String,
    venueName: String,
    onBackClick: () -> Unit,
    viewModel: VenueViewModel = viewModel(),
    addViewModel: AddReviewViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(venueId) {
        viewModel.loadVenueData(venueId)
    }


    if (showDialog) {
        AddReviewDialog(
            onDismiss = { showDialog = false },
            onConfirm = { textoIngresado, estrellasSeleccionadas ->
                addViewModel.comment = textoIngresado
                addViewModel.rating = estrellasSeleccionadas

                addViewModel.saveReview(venueId) {
                    showDialog = false
                    viewModel.loadVenueData(venueId)
                }
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFF070715),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("RESEÑAS", style = MaterialTheme.typography.titleMedium)
                        Text(venueName, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {

            FloatingActionButton(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White,
                onClick = { showDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFE91E63))
            } else if (viewModel.reviews.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No hay reseñas aún", color = Color.White)
                    TextButton(onClick = { showDialog = true }) {
                        Text("SÉ EL PRIMERO EN OPINAR", color = Color(0xFFE91E63))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.reviews) { review ->
                        ReviewItem(review)
                    }
                }
            }
        }
    }
}