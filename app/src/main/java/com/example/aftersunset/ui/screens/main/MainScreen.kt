package com.example.aftersunset.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.aftersunset.data.SampleData
import com.example.aftersunset.navigation.*
import com.example.aftersunset.ui.components.main.CustomBottomBar
import com.example.aftersunset.ui.screens.home.HomeScreen
import com.example.aftersunset.ui.screens.map.MapScreen
import com.example.aftersunset.ui.screens.profile.ProfileScreen
import com.example.aftersunset.ui.screens.tickets.TicketsScreen

/**
 * Pantalla raíz para el flujo autenticado de la aplicación.
 * Este componente centraliza el [Scaffold] que contiene la barra de navegación 
 * inferior y gestiona un [NavHost] interno para las secciones principales:
 * Home, Mapa, Tickets y Perfil.
 *
 * @param rootNavController NavController del grafo principal para permitir 
 * la navegación hacia pantallas fuera del BottomBar.
 */
@RequiresApi(Build.VERSION_CODES.O)
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
            composable<Maps> { backStackEntry ->
                val route: Maps = backStackEntry.toRoute()
                MapScreen(
                    events = SampleData.sampleEvents,
                    onEventClick = { id ->
                        rootNavController.navigate(EventDetail(id))
                    },
                    initialLat = route.lat,
                    initialLng = route.lng
                )
            }
            composable<Tickets> {
                TicketsScreen(
                    tickets = SampleData.sampleTickets,
                    navController = navController
                )
            }
            composable<Profile> {
                ProfileScreen(
                    onLogout = {
                        rootNavController.navigate(AuthGraph)
                    }
                )
            }
        }
    }
}
