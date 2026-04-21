package com.example.aftersunset.ui.screens.checkout

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.checkout.PriceRow
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan

@Composable
fun CheckoutScreen(
    event: Event,
    ticketType: String,
    price: Double,
    onPaymentSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack)
            .padding(24.dp)
    ) {
        Text("RESUMEN DE COMPRA", style = MaterialTheme.typography.labelLarge, color = PacificCyan)

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            color = Color.White.copy(alpha = 0.05f),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(event.title, color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Text(event.clubName, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        PriceRow("Entrada $ticketType", "${price}€")
        PriceRow("Gastos de gestión", "1.50€")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = DividerDefaults.Thickness,
            color = Color.White.copy(alpha = 0.1f)
        )
        PriceRow("TOTAL", "${price + 1.5}€", isTotal = true)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onPaymentSuccess,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Dragonfruit),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("CONFIRMAR Y PAGAR", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}