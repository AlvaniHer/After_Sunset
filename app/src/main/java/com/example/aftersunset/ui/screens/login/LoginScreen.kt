package com.example.aftersunset.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aftersunset.ui.components.CustomLoginField
import com.example.aftersunset.ui.components.SunsetActionButton

/**
 * Pantalla de inicio de sesión de la aplicación.
 * Contiene los campos de credenciales y el acceso al registro.
 * @param onLoginSuccess Callback para navegar al feed tras un acceso exitoso.
 * @param onNavigateToRegister Callback para redirigir al flujo de creación de cuenta.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoBackground(videoResId = com.example.aftersunset.R.raw.v2)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "AFTER SUNSET",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(48.dp))

            CustomLoginField(label = "Email", icon = androidx.compose.material.icons.Icons.Default.Email)

            Spacer(modifier = Modifier.height(16.dp))

            CustomLoginField(label = "Contraseña", icon = androidx.compose.material.icons.Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(32.dp))

            SunsetActionButton(
                text = "ENTRAR",
                modifier = Modifier.fillMaxWidth(),
                onClick = onLoginSuccess
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("¿Sin cuenta? Regístrate", color = Color(0xFF0F98B3))
            }
        }
    }
}