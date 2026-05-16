package com.example.aftersunset.ui.components.auth

import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.R
import com.example.aftersunset.ui.theme.AfterSunsetTheme
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PumpkinSpice
import kotlin.Boolean

/**
 * Componente de campo de entrada de texto personalizado con estética de "cristal".
 *
 * @param label El texto que se muestra como etiqueta o indicación dentro del campo.
 * @param icon El [ImageVector] que se mostrará al inicio del campo (leading icon) para facilitar la identificación visual.
 * @param isPassword Determina si el campo debe aplicar una transformación visual de contraseña para ocultar el texto.
 */
@Composable
fun CustomField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    isError: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, color = Color.White.copy(alpha = 0.6f)) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isError) Color.Red else Color.White.copy(alpha = 0.5f)
            ) },
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible) Icons.Default.RemoveRedEye else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
                }
            }
        },
        visualTransformation = if (isPassword&& !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
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
        ),
        shape = RoundedCornerShape(20.dp),
        singleLine = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0B1E)
@Composable
fun CustomFieldPreview(){
    AfterSunsetTheme{
        CustomField(
            label = stringResource(R.string.email_label),
            icon = Icons.Default.Email,
            isPassword = false,
            value = "",
            onValueChange = {},
        )
    }
}