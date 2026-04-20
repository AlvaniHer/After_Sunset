package com.example.aftersunset.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun ZoneSelector(
    modifier: Modifier = Modifier,
    onZoneSelected: (String) -> Unit
) {
    val zones = listOf("Todos", "Centro", "Teatinos", "Torremolinos", "El Palo")
    var selectedZone by remember { mutableStateOf("Todos") }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(zones) { zone ->
            val isSelected = selectedZone == zone

            Surface(
                onClick = {
                    selectedZone = zone
                    onZoneSelected(zone)
                },
                color = if (isSelected) PacificCyan.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) PacificCyan else Color.White.copy(alpha = 0.1f)
                ),
                modifier = Modifier.height(40.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = zone,
                        color = if (isSelected) PacificCyan else Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}