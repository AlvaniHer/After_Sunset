package com.example.aftersunset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.aftersunset.ui.screens.login.LoginScreen
import com.example.aftersunset.ui.screens.main.MainScreen
import com.example.aftersunset.ui.theme.AfterSunsetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AfterSunsetTheme {
                MainScreen(rootNavController = rememberNavController())
            }
        }
    }
}