package com.example.aftersunset.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import com.example.aftersunset.ui.components.profile.ProfileHeader
import com.example.aftersunset.ui.components.profile.ProfileMenuItem
import com.example.aftersunset.ui.components.profile.StatItem
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.Dragonfruit

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .verticalScroll(rememberScrollState())
            .padding(top = 60.dp)
    ) {
        ProfileHeader(
            name = "Álvaro Pérez",
            location = "Málaga, ES",
            level = "VIP Gold"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem("Eventos", "12")
            StatItem("Puntos", "450")
            StatItem("Siguiendo", "8")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "MI CUENTA",
            color = Color.White.copy(alpha = 0.5f),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        ProfileMenuItem(
            icon = Icons.Default.Favorite,
            label = "Clubes Favoritos",
            onClick = {}
        )
        ProfileMenuItem(
            icon = Icons.AutoMirrored.Filled.List,
            label = "Historial de Noches",
            onClick = {}
        )
        ProfileMenuItem(
            icon = Icons.Default.Settings,
            label = "Ajustes",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onLogout,
            modifier = Modifier.padding(horizontal = 12.dp).align(Alignment.CenterHorizontally)
        ) {
            Text(
                "Cerrar Sesión",
                color = Dragonfruit,
                fontWeight = FontWeight.Bold
            )
        }
    }
}