package com.example.aftersunset.ui.screens.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.screens.checkout.CheckoutViewModel
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun PaymentMethodScreen(
    eventId: String,
    offerName: String,
    price: Double,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit,
    viewModel: CheckoutViewModel = viewModel()
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Scaffold(containerColor = InkBlack) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            Text("DATOS DE PAGO", color = PacificCyan, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("Número de tarjeta") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("MM/AA") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3) cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            SunsetActionButton(
                text = if (viewModel.isProcessing) "PROCESANDO..." else "PAGAR ${price + 1.5}€",
                onClick = {
                    viewModel.processPurchase(
                        eventId = eventId,
                        ticketType = offerName,
                        price = price
                    )
                },
                enabled = cardNumber.length == 16 && cvv.length == 3 && !viewModel.isProcessing,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )
        }
    }

    if (viewModel.purchaseSuccess) {
        LaunchedEffect(Unit) { onPaymentSuccess() }
    }
}