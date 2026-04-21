package com.example.aftersunset.ui.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan
import com.example.aftersunset.ui.theme.Dragonfruit

@Composable
fun ProfileHeader(name: String, location: String, level: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(105.dp)
                    .background(
                        brush = Brush.sweepGradient(listOf(PacificCyan, Dragonfruit, PacificCyan)),
                        shape = CircleShape
                    )
            )
            AsyncImage(
                model = "https://api.dicebear.com/7.x/avataaars/svg?seed=Alvaro",
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, InkBlack, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = name, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Text(text = location, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            color = PacificCyan.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, PacificCyan)
        ) {
            Text(
                text = level.uppercase(),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                color = PacificCyan,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.sp
            )
        }
    }
}

@Preview
@Composable
fun ProfileHeaderPreview(){
    AfterSunsetTheme {
        ProfileHeader(
            name = "Álvaro Pérez",
            location = "Málaga, ES",
            level = "VIP Gold"
        )
    }
}