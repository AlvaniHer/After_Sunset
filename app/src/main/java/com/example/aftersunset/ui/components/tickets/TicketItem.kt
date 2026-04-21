package com.example.aftersunset.ui.components.tickets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.data.SampleData.sampleTickets
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.InkBlack

@Composable
fun TicketItem(
    ticket: Ticket,
    onLocationClick: () -> Unit
) {
    var rotated by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "TicketRotation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { rotated = !rotated }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
    ) {
        if (rotation <= 90f) {
            TicketFront(ticket)
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                TicketBack(ticket, onLocationClick)
            }
        }
    }
}

@Preview
@Composable
fun TicketItemPreview() {
    AfterSunsetTheme {
        Box(modifier = Modifier.background(InkBlack).padding(16.dp)) {
            TicketItem(
                ticket = sampleTickets[0],
                onLocationClick = {}
            )
        }
    }
}
