package com.example.aftersunset.ui.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment
import com.example.aftersunset.ui.components.profile.ProfileHeader
import com.example.aftersunset.ui.components.profile.ProfileMenuItem
import com.example.aftersunset.ui.components.profile.StatItem
import com.example.aftersunset.ui.components.common.SuccessDialog
import com.example.aftersunset.R
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.Dragonfruit
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onFriendsClick: () -> Unit = {},
    onFavoriteClubsClick: () -> Unit = {}
) {
    val user = SampleData.sampleUser  //TODO: BORRAR Y MODIFICAR EL DIALOG CON EL NOMBRE REAL.
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val userName = firebaseUser?.displayName ?: firebaseUser?.email ?: "Usuario"

    val seed = userName.split(" ").firstOrNull() ?: "Usuario"
    val diceBearUrl = "https://api.dicebear.com/9.x/bottts-neutral/svg?seed=$seed"

    val profilePhotoUrl = firebaseUser?.photoUrl?.toString() 
        ?: user.profileImageUrl.takeIf { it.isNotBlank() } 
        ?: diceBearUrl

    var showLevelUpDialog by remember { mutableStateOf(user.pendingLevelUp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .verticalScroll(rememberScrollState())
            .padding(top = 60.dp)
    ) {

        ProfileHeader(
            name = userName,
            location = user.location,
            userLevel = user.level,
            profileImageUrl = profilePhotoUrl
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem("Eventos", user.eventsAttended.toString())
            StatItem("Puntos", user.points.toString())
            StatItem("Siguiendo", user.followingCount.toString())
        }

        Spacer(modifier = Modifier.height(40.dp))

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

        ProfileMenuItem(
            painter = R.drawable.ic_history,
            label = "Historial de Noches",
            onClick = {}
        )

        ProfileMenuItem(
            painter = R.drawable.ic_friends,
            label = "Amigos",
            onClick = onFriendsClick
        )

        ProfileMenuItem(
            icon = Icons.Default.Settings,
            label = "Ajustes",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                SampleData.sampleUser = user.copy(pendingLevelUp = false)
            },
            title = "¡NUEVO RANGO DESBLOQUEADO!",
            message = "Felicidades $userName, has alcanzado el nivel ${user.level}. Tu estatus en After Sunset ha subido de categoría.",
            isLevelUp = true
        )
    }
}
