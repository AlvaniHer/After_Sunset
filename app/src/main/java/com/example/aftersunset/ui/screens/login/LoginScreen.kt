package com.example.aftersunset.ui.screens.login

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
import com.example.aftersunset.ui.components.CustomField
import com.example.aftersunset.ui.components.SunsetActionButton
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PumpkinSpice

/**
 * Pantalla de inicio de sesión de la aplicación.
 * Contiene los campos de credenciales y el acceso al registro.
 * @param onLoginSuccess Función para navegar al feed tras un acceso exitoso.
 * @param onNavigateToRegister Función para redirigir a la pantalla de registro.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoBackground(videoResId = R.raw.login_bg)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                ),
                color = Color.White
            )

            Text(
                text = stringResource(R.string.login_slogan),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Dragonfruit
            )

            Spacer(modifier = Modifier.height(48.dp))

            CustomField(label = stringResource(R.string.email_label), icon = Icons.Default.Email)

            Spacer(modifier = Modifier.height(16.dp))

            CustomField(label = stringResource(R.string.password_label), icon = Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(32.dp))

            SunsetActionButton(
                text = stringResource(R.string.login_button_text),
                modifier = Modifier.fillMaxWidth(),
                onClick = onLoginSuccess
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text(
                    stringResource(R.string.login_register_text1),
                    color = Color.White
                )
                Text(
                    stringResource(R.string.login_register_text2),
                    color = PumpkinSpice,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}