package com.example.aftersunset.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.aftersunset.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val BrandFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Syne"), fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(googleFont = GoogleFont("Syne"), fontProvider = provider, weight = FontWeight.Normal)
)

val ReadableFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Lexend"), fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Lexend"), fontProvider = provider, weight = FontWeight.Normal)
)

val baseline = Typography()

val AfterSunsetTypography = Typography(
    displayLarge = baseline.displayLarge.copy(
        fontFamily = BrandFontFamily,
        fontWeight = FontWeight.Black,
        letterSpacing = (-1).sp
    ),
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = BrandFontFamily,
        fontWeight = FontWeight.Bold
    ),

    bodyLarge = baseline.bodyLarge.copy(
        fontFamily = ReadableFontFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = baseline.labelMedium.copy(
        fontFamily = ReadableFontFamily,
        fontWeight = FontWeight.Medium,
        color = PacificCyan
    )
)

