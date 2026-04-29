package com.example.aftersunset.ui.components.venue

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Sección de información técnica y dirección del local.
 */
@Composable
fun VenueInfoSection(
    address: String,
    capacity: Int,
    minAge: Int,
    onLocationClick: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("INFORMACIÓN", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VenueStatChip("AFORO", capacity.toString())
            VenueStatChip("EDAD", "+$minAge")
            VenueStatChip("RESEÑAS", "4.8 ★")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("DIRECCIÓN", color = PacificCyan, style = MaterialTheme.typography.labelLarge)
        Text(
            text = address,
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Button(
            onClick = onLocationClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(top = 12.dp).height(44.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
        ) {
            Icon(Icons.Default.LocationOn, null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("VER DIRECCIÓN EN EL MAPA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}
