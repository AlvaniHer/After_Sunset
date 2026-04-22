package com.example.aftersunset.ui.components.common

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Encapsula la lógica de vibración para los diálogos de éxito.
 *
 * @param context Contexto de la aplicación para acceder al servicio de vibración.
 * @param isLevelUp Si es true, ejecuta un patrón de doble vibración más intenso.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogVibrationEffect(context: Context, isLevelUp: Boolean) {
    LaunchedEffect(Unit) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        vibrator?.let { v ->
            if (v.hasVibrator()) {
                if (isLevelUp) {
                    v.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 80, 40, 80), -1))
                } else {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }
        }
    }
}
