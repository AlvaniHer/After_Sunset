package com.example.aftersunset.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import com.example.aftersunset.ui.screens.venue.VenueProfileScreen

/**
 * Pantalla raíz para el flujo autenticado de la aplicación.
 * Este componente centraliza el [Scaffold] que contiene la barra de navegación 
 * inferior y gestiona un [NavHost] interno para las secciones principales:
 * Home, Mapa, Tickets y Perfil, además de rutas secundarias como el perfil del local.
 *
 * @param rootNavController NavController del grafo principal para permitir 
 * la navegación hacia pantallas fuera del BottomBar.
 * @param initialLat Latitud inicial para forzar la navegación al mapa si no es nula.
 * @param initialLng Longitud inicial para forzar la navegación al mapa si no es nula.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    initialLat: Double? = null,
    initialLng: Double? = null
) {
    val navController = rememberNavController()

    LaunchedEffect(initialLat, initialLng) {
        if (initialLat != null && initialLng != null) {
            navController.navigate(Maps(initialLat, initialLng)) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
            }
        }
    }

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
            
            composable<VenueProfile> { backStackEntry ->
                val route: VenueProfile = backStackEntry.toRoute()
                VenueProfileScreen(
                    venueId = route.id,
                    onBackClick = { navController.popBackStack() },
                    onEventClick = { eventId ->
                        rootNavController.navigate(EventDetail(eventId))
                    },
                    onLocationClick = { lat, lng ->
                        navController.navigate(Maps(lat, lng)) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
