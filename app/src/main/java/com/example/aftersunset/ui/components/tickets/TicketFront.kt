package com.example.aftersunset.ui.components.tickets

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
 */
@Composable
fun TicketFront(ticket: Ticket) {
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
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = ticket.date,
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
                            text = ticket.entryType.uppercase(),
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
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(modifier = Modifier.padding(6.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.ic_qr_code),
                            contentDescription = "QR Code",
                            tint = Color.Black,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
