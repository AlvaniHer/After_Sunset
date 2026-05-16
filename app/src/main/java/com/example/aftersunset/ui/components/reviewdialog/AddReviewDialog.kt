package com.example.aftersunset.ui.components.reviewdialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Dialogo para crear las nuevas reseñas y no hacer una pantalla entera
 */
@Composable
fun AddReviewDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var stars by remember { mutableIntStateOf(5) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cuéntanos tu experiencia") },
        text = {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    repeat(5) { index ->
                        IconButton(onClick = { stars = index + 1 }) {
                            Icon(
                                imageVector = if (index < stars) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                tint = if (index < stars) Color(0xFFFFD700) else Color.Gray
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Tu comentario") },
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )
            }
        },
        confirmButton = {
            Button(
                enabled = text.isNotEmpty(),
                onClick = { onConfirm(text, stars) }
            ) { Text("Publicar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}