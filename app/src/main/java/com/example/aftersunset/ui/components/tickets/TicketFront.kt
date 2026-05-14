package com.example.aftersunset.ui.components.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.aftersunset.R
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Representa la cara frontal del ticket físico digital.
 * Presenta un diseño estilizado que incluye la imagen del evento, título, club, fecha,
 * tipo de entrada y un código QR de acceso.
 *
 * @param ticket Objeto [Ticket] con la información principal para mostrar en el anverso.
 * @param onVenueClick Callback para navegar al perfil del local.
 */
@Composable
fun TicketFront(
    ticket: Ticket,
    onVenueClick: () -> Unit
) {
    val qrImageUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${ticket.qrCodeData}"
    var showQrDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White.copy(alpha = 0.05f),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ticket.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1.3f)) {
                    Text(
                        text = ticket.eventTitle.uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = ticket.clubName,
                        color = PacificCyan,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onVenueClick() },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = ticket.purchaseDate?.toDate()?.toLocaleString() ?: "Fecha no disponible",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Box(Modifier.weight(0.7f)){
                    Surface(
                        color = Dragonfruit.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = ticket.ticketType.uppercase(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Dragonfruit,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            DashedDivider()

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row {
                        Text(
                            "TICKET ID: ",
                            color = Color.White.copy(alpha = 0.3f),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            ticket.id,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "PUERTAS: 23:00H",
                        color = Color.White.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .size(65.dp)
                ) {
                    AsyncImage(
                        model = qrImageUrl,
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(6.dp)
                            .clickable { showQrDialog = true }
                    )

                }
            }
        }
    }
    if (showQrDialog) {
        Dialog(onDismissRequest = { showQrDialog = false }) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                modifier = Modifier
                    .size(320.dp)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = ticket.eventTitle.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    AsyncImage(
                        model = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=${ticket.qrCodeData}",
                        contentDescription = "QR en grande",
                        modifier = Modifier.size(240.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ESCANEAME PARA ENTRAR",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
