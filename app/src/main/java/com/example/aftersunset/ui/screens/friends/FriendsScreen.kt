package com.example.aftersunset.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.IndigoBloom
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun FriendsScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "MIS\nAMIGOS",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Busca usuarios, revisa solicitudes y consulta tu lista de amigos.",
            color = Color.White.copy(alpha = 0.65f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SearchBoxPlaceholder()

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(title = "SOLICITUDES PENDIENTES")
        Spacer(modifier = Modifier.height(12.dp))
        EmptyCard(message = "Aquí aparecerán las solicitudes que recibas.")

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(title = "MIS AMIGOS")
        Spacer(modifier = Modifier.height(12.dp))
        EmptyCard(message = "Aquí verás tu lista de amigos añadidos.")
    }
}

@Composable
private fun SearchBoxPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(IndigoBloom.copy(alpha = 0.22f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = PacificCyan
        )

        Text(
            text = "Buscar por username",
            color = Color.White.copy(alpha = 0.45f),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White.copy(alpha = 0.55f),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun EmptyCard(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(IndigoBloom.copy(alpha = 0.20f))
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        PacificCyan.copy(alpha = 0.35f),
                        Dragonfruit.copy(alpha = 0.35f)
                    )
                ),
                shape = RoundedCornerShape(26.dp)
            )
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PacificCyan
                )
            }

            Text(
                text = "Sin contenido por ahora",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 14.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalDivider(
            color = Color.White.copy(alpha = 0.08f)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = message,
            color = Color.White.copy(alpha = 0.65f)
        )
    }
}