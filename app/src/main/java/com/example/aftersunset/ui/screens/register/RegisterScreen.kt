package com.example.aftersunset.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.R
import com.example.aftersunset.ui.components.auth.CustomField
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.components.auth.VideoBackground
import com.example.aftersunset.ui.theme.Dragonfruit

//TODO: Meter texto en strings.xml

/**
 * Pantalla de registro de usuario de la aplicación.
 * Contiene los campos de credenciales y el acceso al login.
 * @param onRegisterSuccess Función para navegar al feed tras un registro exitoso.
 * @param onNavigateToLogin Función para redirigir a la pantalla de inicio de sesión.
 */
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoBackground(videoResId = R.raw.auth_bg)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ÚNETE A LA NOCHE",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                ),
                color = Color.White
            )

            Text(
                text = "Crea tu cuenta en After Sunset",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
            )

            Spacer(modifier = Modifier.height(35.dp))

            CustomField(label = "Nombre Completo", icon = Icons.Default.Person)

            Spacer(modifier = Modifier.height(16.dp))

            CustomField(label = stringResource(R.string.email_label), icon = Icons.Default.Email)

            Spacer(modifier = Modifier.height(16.dp))

            CustomField(label = stringResource(R.string.password_label), icon = Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(16.dp))

            CustomField(label = "Repetir Contraseña", icon = Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(32.dp))

            SunsetActionButton(
                text = "REGISTRARSE",
                modifier = Modifier.fillMaxWidth(),
                onClick = onRegisterSuccess
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "¿Ya tienes cuenta?",
                    color = Color.White
                )
                Text(
                    text = " Inicia sesión",
                    color = Dragonfruit,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}