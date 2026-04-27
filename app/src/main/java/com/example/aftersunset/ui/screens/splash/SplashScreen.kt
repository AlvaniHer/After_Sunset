package com.example.aftersunset.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.example.aftersunset.R
import com.example.aftersunset.navigation.AuthGraph
import com.example.aftersunset.ui.theme.InkBlack
import com.example.aftersunset.ui.theme.Dragonfruit
import com.example.aftersunset.ui.theme.PacificCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = Dragonfruit.toArgb(),
            keyPath = arrayOf("surface61825", "Group 1", "**")
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = PacificCyan.toArgb(),
            keyPath = arrayOf("surface61825", "Group 5", "**")
        )
    )

    LaunchedEffect(progress) {
        if (progress == 1f) {
            delay(300)
            navController.navigate(AuthGraph) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkBlack),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = dynamicProperties,
            modifier = Modifier.size(250.dp)
        )
    }
}