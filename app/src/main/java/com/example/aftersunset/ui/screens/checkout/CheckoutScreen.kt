package com.example.aftersunset.ui.screens.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.domain.model.UserLevel
import com.example.aftersunset.ui.components.checkout.PriceRow
import com.example.aftersunset.ui.components.checkout.SuccessDialog
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    event: Event,
    ticketType: String,
    price: Double,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 48.dp).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                }

                Text(
                    text = "RESUMEN DE COMPRA",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = PacificCyan,
                )
            }
        },
        containerColor = InkBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            event.title, 
                            color = Color.White, 
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            event.clubName, 
                            color = Color.White.copy(alpha = 0.6f), 
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            PriceRow("Entrada $ticketType", "${price}€")
            PriceRow("Gastos de gestión", "1.50€")
            
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.1f)
            )
            
            PriceRow("TOTAL", "${price + 1.5}€", isTotal = true)

            Spacer(modifier = Modifier.height(48.dp))

            SunsetActionButton(
                text = "CONFIRMAR Y PAGAR",
                onClick = {
                    val user = SampleData.sampleUser

                    val newTicket = Ticket(
                        id = "TKT-${System.currentTimeMillis()}",
                        eventId = event.id,
                        eventTitle = event.title,
                        clubName = event.clubName,
                        date = event.date,
                        time = "23:30",
                        entryType = ticketType,
                        price = price,
                        qrCodeData = "AS-${event.clubName.uppercase()}-${event.id}-QR",
                        imageUrl = event.imageUrl
                    )

                    val updatedPoints = user.points + 50
                    val updatedEvents = user.eventsAttended + 1

                    val newLevel = when {
                        updatedPoints >= 1000 -> UserLevel.LEGENDARY
                        updatedPoints >= 500 -> UserLevel.GOLD
                        updatedPoints >= 200 -> UserLevel.VIP
                        else -> UserLevel.STANDARD
                    }

                    SampleData.sampleTickets.add(newTicket)

                    SampleData.sampleUser = user.copy(
                        points = updatedPoints,
                        eventsAttended = updatedEvents,
                        level = newLevel,
                        pendingLevelUp = newLevel != user.level
                    )

                    showSuccessDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showSuccessDialog) {
        SuccessDialog(
            onDismiss = onPaymentSuccess,
            title = "¡PAGO COMPLETADO!",
            message = "Gracias por confiar en After Sunset. Tu entrada ya está disponible en la sección de Tickets."
        )
    }
}
