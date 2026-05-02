package com.example.aftersunset.ui.components.tickets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.InkBlack

@Composable
fun DashedDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawLine(
                color = Color.White.copy(alpha = 0.2f),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
                strokeWidth = 2f
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .offset(x = (-10).dp)
                    .size(20.dp)
                    .background(InkBlack, CircleShape)
                    .align(Alignment.CenterStart)
            )
            Box(
                modifier = Modifier
                    .offset(x = (10).dp)
                    .size(20.dp)
                    .background(InkBlack, CircleShape)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview
@Composable
fun DashedDividerPreview() {
    AfterSunsetTheme {
        DashedDivider()
    }
}