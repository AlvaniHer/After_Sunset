package com.example.aftersunset.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.navigation.*
import com.example.aftersunset.ui.components.main.CustomBottomBar
import com.example.aftersunset.ui.screens.home.HomeScreen
import com.example.aftersunset.ui.screens.map.MapScreen
import com.example.aftersunset.ui.screens.profile.ProfileScreen

//TODO: Borrar la lista manual
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
            composable<Maps> {
                MapScreen(
                    events = listOf(
                        Event(
                            id = "1",
                            title = "Neon Ritual",
                            clubName = "Sala Gold",
                            date = "Viernes, 24 Mayo",
                            price = 15.0,
                            imageUrl = "https://picsum.photos/id/123/800/600",
                            genre = "Techno",
                            tags = listOf("Centro", "VIP", "Luces LED"),
                            zone = "Málaga Centro",
                            fullAddress = "C. Luis de Velázquez, 5, 29008 Málaga",
                            latitude = 36.7218,
                            longitude = -4.4185,
                            description = "Vive la experiencia techno más exclusiva en el corazón de Málaga. Sonido Funktion-One y el mejor ambiente.",
                            minAge = 21,
                            avgAge = 26,
                            capacity = 500,
                            isSoldOut = false
                        ),
                        Event(
                            id = "2",
                            title = "Sunset Beats",
                            clubName = "Moliere Playa",
                            date = "Sábado, 25 Mayo",
                            price = 20.0,
                            imageUrl = "https://picsum.photos/id/158/800/600",
                            genre = "House",
                            tags = listOf("Beach Club", "Vistas al Mar", "Terraza"),
                            zone = "Torremolinos / Los Álamos",
                            fullAddress = "Paseo Marítimo Los Álamos, s/n, 29620 Torremolinos",
                            latitude = 36.6436,
                            longitude = -4.4674,
                            description = "Baila bajo las estrellas frente al Mediterráneo. La fiesta comienza al atardecer y no para hasta el amanecer.",
                            minAge = 21,
                            avgAge = 26,
                            capacity = 500,
                            isSoldOut = false
                        ),
                        Event(
                            id = "3",
                            title = "Urban Jungle",
                            clubName = "The Sound",
                            date = "Jueves, 23 Mayo",
                            price = 10.0,
                            imageUrl = "https://picsum.photos/id/249/800/600",
                            genre = "Reggaeton",
                            tags = listOf("Universitario", "Barra Libre", "Teatinos"),
                            zone = "Teatinos",
                            fullAddress = "C. Plutarco, 58, 29010 Málaga",
                            latitude = 36.7165,
                            longitude = -4.4712,
                            description = "La noche de los jueves en Teatinos tiene un nombre. Música urbana y los mejores cócteles de la zona.",
                            minAge = 21,
                            avgAge = 26,
                            capacity = 500,
                            isSoldOut = false
                        ),
                        Event(
                            id = "4",
                            title = "Electronic Culture",
                            clubName = "París 15",
                            date = "Sábado, 1 Junio",
                            price = 25.0,
                            imageUrl = "https://picsum.photos/id/321/800/600",
                            genre = "Hard Techno",
                            tags = listOf("Concierto", "Aforo +3000", "Sonido Pro"),
                            zone = "Polígono San Luis",
                            fullAddress = "C. la Orotava, 27, 29006 Málaga",
                            latitude = 36.7032,
                            longitude = -4.4563,
                            description = "El templo de la electrónica en el sur de España. Un despliegue de luces y sonido que te dejará sin aliento.",
                            minAge = 21,
                            avgAge = 26,
                            capacity = 500,
                            isSoldOut = false
                        )
                    ),
                    onEventClick = { id ->
                        rootNavController.navigate(EventDetail(id))
                    }
                )
            }
            composable<Tickets> { }
            composable<Profile> {
                ProfileScreen(
                    onLogout = {
                        rootNavController.navigate(AuthGraph)
                    },
                    onNavigateToTickets = { }
                )
            }
        }
    }
}