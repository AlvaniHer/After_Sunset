package com.example.aftersunset.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.aftersunset.data.SampleData.sampleEvents
import com.example.aftersunset.ui.screens.checkout.CheckoutScreen
import com.example.aftersunset.ui.screens.event.EventDetailScreen
import com.example.aftersunset.ui.screens.login.LoginScreen
import com.example.aftersunset.ui.screens.main.MainScreen
import com.example.aftersunset.ui.screens.register.RegisterScreen
import com.example.aftersunset.ui.screens.splash.SplashScreen

/**
 * Componente central de navegación que orquesta todos los destinos de la app.
 * * Gestiona dos grafos principales:
 * 1. [AuthGraph]: Maneja el acceso y registro.
 * 2. [MainGraph]: Maneja las pestañas principales y detalles de eventos.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(navController = navController)
        }
        /**
         * Grafo de Autenticación.
         * Se encarga del flujo inicial antes de que el usuario acceda al contenido.
         */
        navigation<AuthGraph>(startDestination = Login) {
            composable<Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(MainGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Register) }
                )
            }

            composable<Register> {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(MainGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }
        }

        /**
         * Grafo Principal.
         * Contiene la lógica de las secciones principales accesibles mediante el Bottom Nav.
         */
        navigation<MainGraph>(startDestination = Main) {
            composable<Main> {
                MainScreen(rootNavController = navController)
            }

            composable<EventDetail>(
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    ) + fadeIn()
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(400)
                    ) + fadeOut()
                }
            ) { backStackEntry ->
                val detail: EventDetail = backStackEntry.toRoute()
                EventDetailScreen(
                    eventId = detail.id,
                    onBackClick = { navController.popBackStack() },
                    onBuyClick = { eventId, ticketType, price ->
                        navController.navigate(Checkout(eventId, ticketType, price))
                    }
                )
            }

            composable<Checkout> { backStackEntry ->
                val route: Checkout = backStackEntry.toRoute()
                val event = sampleEvents.find { it.id == route.eventId }

                if (event != null) {
                    CheckoutScreen(
                        event = event,
                        ticketType = route.ticketType,
                        price = route.price,
                        onBackClick = { navController.popBackStack() },
                        onPaymentSuccess = {
                            navController.navigate(Main) {
                                popUpTo<Main> { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
