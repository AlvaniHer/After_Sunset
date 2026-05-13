package com.example.aftersunset.ui.components.venue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Review

/**
 * Sección de reseñas del local.
 * Muestra una lista horizontal de opiniones de usuarios.
 */
@Composable
fun VenueReviewsSection(reviews: List<Review> ) {
    Column {
        if (reviews.isEmpty()) {
            Text(
                "Aún no hay reseñas",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(end = 24.dp)
            ) {
                items(reviews) { review ->
                    ReviewItem(review)
                }
            }
        }
    }
}