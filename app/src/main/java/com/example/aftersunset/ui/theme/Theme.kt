package com.example.aftersunset.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class AfterSunsetColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val accent1: Color,
    val accent2: Color,
    val neonGold: Color,
    val electricYellow: Color,
    val deepViolet: Color,
    val onBackground: Color = Color.White
)

val LocalAfterSunsetColors = staticCompositionLocalOf {
    AfterSunsetColors(
        background = InkBlack,
        surface = InkBlackLight,
        primary = IndigoBloom,
        secondary = PacificCyanGlow,
        accent1 = Dragonfruit,
        accent2 = PumpkinSpice,
        neonGold = NeonGold,
        electricYellow = ElectricYellow,
        deepViolet = DeepViolet
    )
}

class AfterSunsetGradients(
    val actionGradient: Brush,
    val borderGradient: Brush,
    val legendaryGradient: Brush
)

object AfterSunsetTheme {
    val colors: AfterSunsetColors
        @Composable
        get() = LocalAfterSunsetColors.current

    val gradients: AfterSunsetGradients
        @Composable
        get() = AfterSunsetGradients(
            actionGradient = Brush.horizontalGradient(listOf(Dragonfruit, PumpkinSpice)),
            borderGradient = Brush.linearGradient(listOf(IndigoBloom, Dragonfruit)),
            legendaryGradient = Brush.sweepGradient(listOf(Dragonfruit, NeonGold, ElectricYellow, Dragonfruit))
        )
}

@Composable
fun AfterSunsetTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAfterSunsetColors provides AfterSunsetColors(
        background = InkBlack,
        surface = InkBlackLight,
        primary = IndigoBloom,
        secondary = PacificCyan,
        accent1 = Dragonfruit,
        accent2 = PumpkinSpice,
        neonGold = NeonGold,
        electricYellow = ElectricYellow,
        deepViolet = DeepViolet
    )) {
        MaterialTheme(
            colorScheme = darkColorScheme(
                background = AfterSunsetTheme.colors.background,
                surface = AfterSunsetTheme.colors.surface,
                primary = AfterSunsetTheme.colors.primary,
                secondary = AfterSunsetTheme.colors.secondary
            ),
            typography = AfterSunsetTypography,
            shapes = AfterSunsetShapes,
            content = content
        )
    }
}
