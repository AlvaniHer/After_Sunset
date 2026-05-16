package com.example.aftersunset.ui.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.R
import com.example.aftersunset.ui.components.common.SuccessDialog
import com.example.aftersunset.ui.components.profile.ProfileHeader
import com.example.aftersunset.ui.components.profile.ProfileMenuItem
import com.example.aftersunset.ui.components.profile.StatItem
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onFriendsClick: () -> Unit = {},
    onFavoriteClubsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
) {
    val user = viewModel.user
    var showLevelUpDialog by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadUserProfile()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(user) {
        if (user?.pendingLevelUp == true) {
            showLevelUpDialog = true
        }
    }

    if (viewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Dragonfruit
            )
        }
    } else if (user != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(InkBlack)
                .verticalScroll(rememberScrollState())
                .padding(top = 60.dp)
        ) {
            ProfileHeader(
                name = user.username,
                location = user.location,
                userLevel = user.level,
                profileImageUrl = user.getAvatarUrl()
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Eventos",
                    value = user.eventsAttended.toString()
                )

                StatItem(
                    label = "Puntos",
                    value = user.points.toString()
                )

                StatItem(
                    label = "Siguiendo",
                    value = user.followingCount.toString()
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Text(
                text = "MI CUENTA",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            ProfileMenuItem(
                icon = Icons.Default.Favorite,
                label = "Clubes Favoritos",
                onClick = onFavoriteClubsClick
            )

//            ProfileMenuItem(
//                painter = R.drawable.ic_history,
//                label = "Historial de Noches",
//                onClick = {}
//            )

            ProfileMenuItem(
                painter = R.drawable.ic_friends,
                label = "Amigos",
                onClick = onFriendsClick
            )

            ProfileMenuItem(
                icon = Icons.Default.Settings,
                label = "Ajustes",
                onClick = onSettingsClick
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            TextButton(
                onClick = onLogout,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    color = Dragonfruit,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showLevelUpDialog) {
            SuccessDialog(
                onDismiss = {
                    showLevelUpDialog = false
                },
                title = "¡NUEVO RANGO DESBLOQUEADO!",
                message = "Felicidades ${user.name}, has alcanzado el nivel ${user.level}.",
                isLevelUp = true
            )
        }
    }
}