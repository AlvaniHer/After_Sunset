package com.example.aftersunset.ui.screens.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.components.common.SunsetActionButton
import com.example.aftersunset.ui.screens.checkout.CheckoutViewModel
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.PacificCyan
import com.example.aftersunset.ui.components.checkout.OfferCard

@Composable
fun OfferSelectionScreen(
    eventId: String,
    onBackClick: () -> Unit,
    onOfferSelected: (String, Double) -> Unit,
    viewModel: CheckoutViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.loadCheckoutData(eventId)
    }

    val event = viewModel.event
    val offers = viewModel.eventOffers
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = InkBlack,
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
                    text = "SELECCIONA TU ENTRADA",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = PacificCyan,
                )
            }
        }
    ) { padding ->
        if (viewModel.isProcessing) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PacificCyan)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                event?.let {
                    Text(
                        it.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(it.clubName, color = PacificCyan)
                }

                Spacer(modifier = Modifier.height(24.dp))

                offers.forEachIndexed { index, offer ->
                    OfferCard(
                        offer = offer,
                        isSelected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                SunsetActionButton(
                    text = "CONTINUAR",
                    onClick = {
                        if (offers.isNotEmpty()) {
                            val selectedOffer = offers[selectedIndex]
                            onOfferSelected(selectedOffer.titulo, selectedOffer.precio)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = true
                )

            }
        }
    }
}