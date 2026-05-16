package com.example.aftersunset.ui.screens.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.aftersunset.ui.components.checkout.PriceRow
import com.example.aftersunset.ui.components.common.SuccessDialog
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Pantalla de pasarela de pago.
 * Muestra un resumen del evento seleccionado, el desglose de precios y permite confirmar la compra.
 * Gestiona la lógica de generación de tickets y actualización de puntos/nivel del usuario.
 *
 * @param eventId Identificador del evento.
 * @param ticketType Tipo de entrada seleccionada.
 * @param price Precio base de la entrada.
 * @param onBackClick Callback para regresar a la pantalla anterior.
 * @param onPaymentSuccess Callback que se ejecuta tras un pago exitoso para navegar a la siguiente sección.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    eventId: String,
    ticketType: String,
    price: Double,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit,
    viewModel: CheckoutViewModel = viewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.loadCheckoutData(eventId)
    }
    val event = viewModel.event

    if (event == null && viewModel.errorMessage == null) {
        Box(modifier = Modifier.fillMaxSize().background(InkBlack), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PacificCyan)
        }
    } else if (viewModel.errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize().background(InkBlack),
            contentAlignment = Alignment.Center
        ) {
            Text(viewModel.errorMessage!!, color = Color.White, textAlign = TextAlign.Center)
        }
    } else if (event != null) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
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
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(event.title, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(event.clubName, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text("MÉTODO DE PAGO", color = PacificCyan, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 12.dp))

                var cardNumber by remember { mutableStateOf("") }
                var showCardError by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16) {
                            cardNumber = it
                            if (showCardError) showCardError = false
                        }
                    },
                    label = { Text("Número de tarjeta", color = Color.Gray) },
                    placeholder = { Text("0000 0000 0000 0000", color = Color.White.copy(0.3f)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showCardError,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White.copy(0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                if (showCardError) {
                    Text(
                        text = "Introduce una tarjeta válida",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                PriceRow("Entrada $ticketType", "${price}€")
                PriceRow("Gastos de gestión", "1.50€")
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.White.copy(0.1f))
                PriceRow("TOTAL", "${price + 1.5}€", isTotal = true)

                Spacer(modifier = Modifier.height(32.dp))

                SunsetActionButton(
                    text = if (viewModel.isProcessing) "PROCESANDO..." else "PAGAR AHORA",
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    onClick = {
                        if (cardNumber.length < 16) {
                            showCardError = true
                        } else {
                            showCardError = false
                            viewModel.processPurchase(eventId, ticketType, price)
                        }
                    },
                    enabled = !viewModel.isProcessing
                )
            }
        }
    }

    if (viewModel.purchaseSuccess) {
        SuccessDialog(
            onDismiss = onPaymentSuccess,
            title = "¡PAGO COMPLETADO!",
            message = "Gracias por confiar en After Sunset. Tu entrada ya está disponible en la sección de Tickets."
        )
    }
}