package com.example.aftersunset.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.aftersunset.feature.friends.model.FriendUser
import com.example.aftersunset.feature.friends.viewmodel.FriendsViewModel
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.IndigoBloom
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun FriendsScreen(
    onBackClick: () -> Unit,
    viewModel: FriendsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.startObserving()
    }

    val currentUserId = viewModel.getCurrentUserId()
    val searchText = viewModel.searchText
    val foundUser = viewModel.foundUser
    val pendingRequests = viewModel.pendingRequests
    val acceptedFriendships = viewModel.acceptedFriendships
    val friendUsers = viewModel.friendUsers
    val isLoading = viewModel.isLoading
    val message = viewModel.message

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "MIS\nAMIGOS",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Busca usuarios, revisa solicitudes y consulta tu lista de amigos.",
            color = Color.White.copy(alpha = 0.65f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { viewModel.onSearchTextChange(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = PacificCyan
                )
            },
            placeholder = {
                Text(
                    text = "Buscar por username",
                    color = Color.White.copy(alpha = 0.45f)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IndigoBloom.copy(alpha = 0.22f),
                unfocusedContainerColor = IndigoBloom.copy(alpha = 0.22f),
                disabledContainerColor = IndigoBloom.copy(alpha = 0.22f),
                focusedBorderColor = PacificCyan,
                unfocusedBorderColor = Color.White.copy(alpha = 0.10f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = PacificCyan
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.searchUser() },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Dragonfruit,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = if (isLoading) "Buscando..." else "Buscar usuario")
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            MessageCard(message = message)
        }

        if (foundUser != null) {
            Spacer(modifier = Modifier.height(20.dp))
            UserResultCard(
                user = foundUser.second,
                onAddClick = { viewModel.sendFriendRequest() },
                isLoading = isLoading
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(title = "SOLICITUDES PENDIENTES")
        Spacer(modifier = Modifier.height(12.dp))

        if (pendingRequests.isEmpty()) {
            EmptyCard(message = "Aquí aparecerán las solicitudes que recibas.")
        } else {
            pendingRequests.forEach { (friendshipId, friendship) ->
                val user = friendUsers[friendship.id_usuario_emisor]

                PendingRequestCard(
                    user = user,
                    fallbackUserId = friendship.id_usuario_emisor,
                    onAcceptClick = { viewModel.acceptFriendRequest(friendshipId) },
                    isLoading = isLoading
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(title = "MIS AMIGOS")
        Spacer(modifier = Modifier.height(12.dp))

        if (acceptedFriendships.isEmpty()) {
            EmptyCard(message = "Aquí verás tu lista de amigos añadidos.")
        } else {
            acceptedFriendships.forEach { (_, friendship) ->
                val otherUserId = if (friendship.id_usuario_emisor == currentUserId) {
                    friendship.id_usuario_receptor
                } else {
                    friendship.id_usuario_emisor
                }

                val user = friendUsers[otherUserId]

                if (user != null) {
                    FriendCard(user = user)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun MessageCard(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(IndigoBloom.copy(alpha = 0.20f))
            .border(
                width = 1.dp,
                color = Dragonfruit.copy(alpha = 0.45f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = message,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}

@Composable
private fun UserResultCard(
    user: FriendUser,
    onAddClick: () -> Unit,
    isLoading: Boolean
) {
    CardContainer {
        UserInfo(user = user)

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onAddClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = PacificCyan,
                contentColor = InkBlack
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = if (isLoading) "Enviando..." else "Añadir amigo")
        }
    }
}

@Composable
private fun PendingRequestCard(
    user: FriendUser?,
    fallbackUserId: String,
    onAcceptClick: () -> Unit,
    isLoading: Boolean
) {
    CardContainer {
        if (user != null) {
            UserInfo(user = user)
        } else {
            UserFallbackInfo(fallbackUserId)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onAcceptClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Dragonfruit,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = if (isLoading) "Procesando..." else "Aceptar solicitud")
        }
    }
}

@Composable
private fun FriendCard(user: FriendUser) {
    CardContainer {
        UserInfo(user = user)
    }
}

@Composable
private fun UserInfo(user: FriendUser) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = user.displayAvatarUrl(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.padding(start = 14.dp)
        ) {
            Text(
                text = user.displayName(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = user.displayUsername(),
                color = Color.White.copy(alpha = 0.60f)
            )
        }
    }
}

@Composable
private fun UserFallbackInfo(fallbackUserId: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            val seed = fallbackUserId.take(8)
            AsyncImage(
                model = "https://api.dicebear.com/9.x/bottts-neutral/svg?seed=$seed",
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.padding(start = 14.dp)
        ) {
            Text(
                text = "Usuario",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "@${fallbackUserId.take(8)}...",
                color = Color.White.copy(alpha = 0.60f)
            )
        }
    }
}

@Composable
private fun CardContainer(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(IndigoBloom.copy(alpha = 0.20f))
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        PacificCyan.copy(alpha = 0.35f),
                        Dragonfruit.copy(alpha = 0.35f)
                    )
                ),
                shape = RoundedCornerShape(26.dp)
            )
            .padding(18.dp),
        content = content
    )
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White.copy(alpha = 0.55f),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun EmptyCard(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(IndigoBloom.copy(alpha = 0.20f))
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        PacificCyan.copy(alpha = 0.35f),
                        Dragonfruit.copy(alpha = 0.35f)
                    )
                ),
                shape = RoundedCornerShape(26.dp)
            )
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PacificCyan
                )
            }

            Text(
                text = "Sin contenido por ahora",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 14.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalDivider(
            color = Color.White.copy(alpha = 0.08f)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = message,
            color = Color.White.copy(alpha = 0.65f)
        )
    }
}