package com.example.aftersunset.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftersunset.navigation.*
import com.example.aftersunset.ui.components.CustomBottomBar
import com.example.aftersunset.ui.screens.home.HomeScreen

/**
 * Pantalla raíz para el flujo autenticado de la aplicación.
 * Este componente centraliza el [Scaffold] que contiene la barra de navegación 
 * inferior y gestiona un [NavHost] interno para las secciones principales:
 * Home, Mapa, Tickets y Perfil.
 *
 * @param rootNavController NavController del grafo principal para permitir 
 * la navegación hacia pantallas fuera del BottomBar.
 */
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Home> {
                HomeScreen(
                    onEventClick = { id ->
                        rootNavController.navigate(EventDetail(id))
                    }
                )
            }
            composable<Maps> { }
            composable<Tickets> { }
            composable<Profile> { }
        }
    }
}