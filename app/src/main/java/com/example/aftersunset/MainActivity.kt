package com.example.aftersunset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aftersunset.navigation.Navigation
import com.example.aftersunset.ui.theme.AfterSunsetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false }
        setContent {
            AfterSunsetTheme {
                Navigation()
            }
        }
    }
}