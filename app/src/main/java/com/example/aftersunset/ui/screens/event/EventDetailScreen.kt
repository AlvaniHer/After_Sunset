package com.example.aftersunset.ui.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.event.DetailInfoCard
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.components.event.EventContent
import com.example.aftersunset.ui.components.event.EventStatsRow
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

//TODO: Documentar la funcionalidad de esta pantalla y meter texto en strings
@Composable
fun EventDetailScreen(
    eventId: String,
    onBackClick: () -> Unit
) {
    val event = sampleEvents.find { it.id == eventId }
    if (event == null)
        Text(text = "Evento no encontrado") // TODO: Hacer una pantalla de error para este caso
    else {
        EventContent(event, onBackClick)
    }
}