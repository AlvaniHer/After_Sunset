package com.example.aftersunset.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.aftersunset.ui.screens.checkout.CheckoutScreen
import com.example.aftersunset.ui.screens.event.EventDetailScreen
import com.example.aftersunset.ui.screens.friends.FriendsScreen
import com.example.aftersunset.ui.screens.login.LoginScreen
import com.example.aftersunset.ui.screens.main.MainScreen
import com.example.aftersunset.ui.screens.profile.FavoriteClubsScreen
import com.example.aftersunset.ui.screens.register.RegisterScreen
import com.example.aftersunset.ui.screens.reviews.ReviewsScreen
import com.example.aftersunset.ui.screens.settings.ProfileSettingsScreen
import com.example.aftersunset.ui.screens.splash.SplashScreen
import com.example.aftersunset.ui.screens.venue.VenueProfileScreen

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
        navigation<MainGraph>(startDestination = Main()) {
            composable<Main> { backStackEntry ->
                val route: Main = backStackEntry.toRoute()
                MainScreen(
                    rootNavController = navController,
                    initialLat = route.lat,
                    initialLng = route.lng,
                    initialTab = route.initialTab
                )
            }

            composable<Friends> {
                FriendsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable<FavoriteClubs> {
                FavoriteClubsScreen(
                    navController = navController
                )
            }

            composable<ProfileSettings> {
                ProfileSettingsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable<EventDetail> { backStackEntry ->
                val detail: EventDetail = backStackEntry.toRoute()
                EventDetailScreen(
                    eventId = detail.id,
                    onBackClick = { navController.popBackStack() },
                    onVenueClick = { venueId ->
                        navController.navigate(VenueProfile(venueId))
                    },
                    onBuyClick = { eventId, ticketType, price ->
                        navController.navigate(Checkout(eventId, ticketType, price))
                    },
                    onLocationClick = { lat, lng ->
                        navController.navigate(Main(lat, lng)) {
                            popUpTo<Main> { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<VenueProfile> { backStackEntry ->
                val profile: VenueProfile = backStackEntry.toRoute()

                VenueProfileScreen(
                    venueId = profile.id,
                    onBackClick = { navController.popBackStack() },
                    onEventClick = { id ->
                        navController.navigate(EventDetail(id))
                    },
                    onLocationClick = { lat, lng ->
                        navController.navigate(Main(lat, lng)) {
                            popUpTo<Main> { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSeeAllReviewsClick = { id, name ->
                        navController.navigate(Reviews(id, name))
                    }
                )
            }

            composable<Reviews> { backStackEntry ->
                val route: Reviews = backStackEntry.toRoute()
                ReviewsScreen(
                    venueId = route.id,
                    venueName = route.name,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable<Checkout> { backStackEntry ->
                val route: Checkout = backStackEntry.toRoute()

                CheckoutScreen(
                    eventId = route.eventId,
                    ticketType = route.ticketType,
                    price = route.price,
                    onBackClick = { navController.popBackStack() },
                    onPaymentSuccess = {
                        navController.navigate(Main(initialTab = 2)) {
                            popUpTo<MainGraph> { inclusive = false }
                        }
                    }
                )
            }
        }
    }
}
