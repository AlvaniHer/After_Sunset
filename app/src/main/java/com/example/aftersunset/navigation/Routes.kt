package com.example.aftersunset.navigation

import kotlinx.serialization.Serializable

/**
 * Pantalla de carga inicial.
 */
@Serializable object Splash

/**
 * Grafo de navegación para el flujo de autenticación (Login y Registro).
 */
@Serializable object AuthGraph

/**
 * Grafo de navegación principal para la experiencia de usuario logueado.
 */
@Serializable object MainGraph

/**
 * Destino raíz para el grafo principal que contiene el Scaffold con BottomBar.
 */
@Serializable object Main

/**
 * Destino para la pantalla de inicio de sesión.
 */
@Serializable object Login

/**
 * Destino para la pantalla de registro de nuevos usuarios.
 */
@Serializable object Register

/**
 * Destino para el Feed principal de recomendaciones y eventos.
 */
@Serializable object Home

/**
 * Destino para la vista de mapa interactivo.
 * Se permiten coordenadas opcionales para centrar el mapa en un local específico.
 */
@Serializable data class Maps(
    val lat: Double? = null,
    val lng: Double? = null
)

/**
 * Destino para la gestión de entradas compradas.
 */
@Serializable object Tickets

/**
 * Destino para el perfil del usuario, favoritos y ajustes.
 */
@Serializable object Profile

/**
 * Destino para el detalle de un evento específico.
 * @param id Identificador único del evento.
 */
@Serializable data class EventDetail(val id: String)

/**
 * Pantalla de perfil de una discoteca u organizador.
 * @param id Identificador de la discoteca.
 */
@Serializable data class VenueProfile(val id: String)

/**
 * Pantalla de pasarela de pago y confirmación de compra.
 * @param eventId ID del evento que el usuario está adquiriendo.
 */
@Serializable data class Checkout(
    val eventId: String,
    val ticketType: String,
    val price: Double
)