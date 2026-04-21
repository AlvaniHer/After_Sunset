package com.example.aftersunset.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.Dragonfruit

@Composable
fun PriceRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = if (isTotal) 8.dp else 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium,
            color = if (isTotal) Color.White else Color.White.copy(alpha = 0.6f),
            fontWeight = if (isTotal) FontWeight.Black else FontWeight.Normal
        )
        Text(
            text = value,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium,
            color = if (isTotal) Dragonfruit else Color.White,
            fontWeight = if (isTotal) FontWeight.Black else FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PriceRowPreview(){
    AfterSunsetTheme {
        PriceRow(
            label = "Precio",
            value = "10€",
            isTotal = true
        )
    }
}