package com.example.aftersunset.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aftersunset.R
import com.example.aftersunset.navigation.Home
import com.example.aftersunset.navigation.Maps
import com.example.aftersunset.navigation.Tickets
import com.example.aftersunset.navigation.Profile
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Barra de navegación inferior.
 * @param navController El controlador de navegación del contenedor principal.
 */
@Composable
fun CustomBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(72.dp),
        shape = MaterialTheme.shapes.large,
        color = AfterSunsetTheme.colors.surface.copy(alpha = 0.85f),
        border = BorderStroke(1.dp, AfterSunsetTheme.gradients.borderGradient)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                isSelected = currentDestination?.hasRoute<Home>() == true
            ) {
                navigateToTab(navController, Home)
            }

            BottomNavItem(
                icon = Icons.Default.Place,
                isSelected = currentDestination?.hasRoute<Maps>() == true
            ) {
                navigateToTab(navController, Maps)
            }

            BottomNavItem(
                painter = R.drawable.ic_confirmation_number,
                isSelected = currentDestination?.hasRoute<Tickets>() == true,
                onClick = {
                    navigateToTab(navController, Tickets)
                }
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                isSelected = currentDestination?.hasRoute<Profile>() == true,
                onClick = {
                    navigateToTab(navController, Profile)
                }
            )
        }
    }
}

/**
 * Función auxiliar para navegar entre pestañas evitando duplicados en la pila.
 */
private fun navigateToTab(navController: NavHostController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
fun CustomBottomBarPreview(){
    AfterSunsetTheme{
        CustomBottomBar(navController = NavHostController(LocalContext.current))
    }
}