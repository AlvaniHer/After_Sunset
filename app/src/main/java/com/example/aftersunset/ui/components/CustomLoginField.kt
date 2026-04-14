package com.example.aftersunset.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PumpkinSpice

/**
 * Componente de campo de entrada de texto personalizado con estética de "cristal".
 *
 * @param label El texto que se muestra como etiqueta o indicación dentro del campo.
 * @param icon El [ImageVector] que se mostrará al inicio del campo (leading icon) para facilitar la identificación visual.
 * @param isPassword Determina si el campo debe aplicar una transformación visual de contraseña para ocultar el texto.
 */
@Composable
fun CustomLoginField(
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.5f)) },
        shape = RoundedCornerShape(20.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Dragonfruit,
            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
            focusedContainerColor = Color.White.copy(alpha = 0.05f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Dragonfruit,
            unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
            cursorColor = PumpkinSpice
        )
    )
}