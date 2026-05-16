package com.example.aftersunset.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.InkBlackLight
import com.example.aftersunset.ui.theme.PacificCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileSettingsViewModel = viewModel()
) {
    Scaffold(
        containerColor = InkBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ajustes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = InkBlack
                )
            )
        }
    ) { paddingValues ->
        if (viewModel.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(InkBlack)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Dragonfruit
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(InkBlack)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Gestiona tu cuenta",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Modifica tu nombre de usuario o cambia tu contraseña.",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = InkBlackLight.copy(alpha = 0.92f)
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.08f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        Text(
                            text = "Nombre de usuario",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.username,
                            onValueChange = {
                                viewModel.username = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Nombre de usuario"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null
                                )
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = PacificCyan,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.25f),
                                focusedLabelColor = PacificCyan,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.55f),
                                cursorColor = PacificCyan,
                                focusedLeadingIconColor = PacificCyan,
                                unfocusedLeadingIconColor = Color.White.copy(alpha = 0.55f)
                            )
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = InkBlackLight.copy(alpha = 0.92f)
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.08f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        Text(
                            text = "Cambiar contraseña",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Deja estos campos vacíos si no quieres cambiarla.",
                            color = Color.White.copy(alpha = 0.55f),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.newPassword,
                            onValueChange = {
                                viewModel.newPassword = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Nueva contraseña"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null
                                )
                            },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = PacificCyan,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.25f),
                                focusedLabelColor = PacificCyan,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.55f),
                                cursorColor = PacificCyan,
                                focusedLeadingIconColor = PacificCyan,
                                unfocusedLeadingIconColor = Color.White.copy(alpha = 0.55f)
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        OutlinedTextField(
                            value = viewModel.confirmPassword,
                            onValueChange = {
                                viewModel.confirmPassword = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Confirmar contraseña"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null
                                )
                            },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = PacificCyan,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.25f),
                                focusedLabelColor = PacificCyan,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.55f),
                                cursorColor = PacificCyan,
                                focusedLeadingIconColor = PacificCyan,
                                unfocusedLeadingIconColor = Color.White.copy(alpha = 0.55f)
                            )
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                viewModel.errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Dragonfruit,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }

                viewModel.successMessage?.let { message ->
                    Text(
                        text = message,
                        color = PacificCyan,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }

                if (viewModel.isSaving) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Dragonfruit,
                        trackColor = Color.White.copy(alpha = 0.12f)
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }

                Button(
                    onClick = {
                        viewModel.saveChanges()
                    },
                    enabled = !viewModel.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Dragonfruit,
                        contentColor = Color.White,
                        disabledContainerColor = Dragonfruit.copy(alpha = 0.4f),
                        disabledContentColor = Color.White.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    Text(
                        text = if (viewModel.isSaving) "Guardando..." else "Guardar cambios",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}
