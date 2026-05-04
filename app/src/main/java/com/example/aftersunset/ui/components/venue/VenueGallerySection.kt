package com.example.aftersunset.ui.components.venue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

/**
 * Sección de galería que muestra fotos del ambiente del local.
 */
@Composable
fun VenueGallerySection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("LA SALA", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 24.dp)
        ) {
            items(3) { index ->
                Surface(
                    modifier = Modifier.size(width = 200.dp, height = 120.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.05f)
                ) {
                    AsyncImage(
                        model = "https://picsum.photos/id/${100 + index}/400/240",
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
