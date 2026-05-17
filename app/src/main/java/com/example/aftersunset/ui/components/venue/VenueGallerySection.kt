package com.example.aftersunset.ui.components.venue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
    val galleryImages = listOf(
        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/fe/39/18/disco-tropics-makes-your.jpg?w=900&h=500&s=1",
        "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgfQRLV9vE1SBB8tKe6hSyp9xh9mUDCd1lgVsuNAMD0WjBAOUBusLKO3iIfwVSFxxMHslFdMQpw-oqZjuNRYbVfNWKvFwCHpc_jAPzDsRCDvW1hgJ4zEv71ogvKDhWa0SjxB0gL3K5ICR0S/s1600/disco_43.jpg",
        "https://discovips.cat/wp-content/uploads/2022/06/sala-general-10.jpg"
    )

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("LA SALA", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 24.dp)
        ) {
            items(galleryImages) { imageUrl ->
                Surface(
                    modifier = Modifier.size(width = 200.dp, height = 120.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.05f)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Foto de la sala",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
