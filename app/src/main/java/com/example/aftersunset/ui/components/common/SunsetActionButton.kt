package com.example.aftersunset.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.R
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Componente de botón principal con el degradado de la marca.
 * @param text Texto que mostrará el botón.
 * @param onClick Acción a ejecutar al pulsar.
 * @param modifier Modificador para ajustar tamaño o posición.
 */
@Composable
fun SunsetActionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(28.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFFEB4990), Color(0xFFFF6B00))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0B1E)
@Composable
fun SunsetActionButtonPreview(){
    AfterSunsetTheme{
        SunsetActionButton(
            text = stringResource(R.string.login_button_text),
            onClick = {}
        )
    }
}