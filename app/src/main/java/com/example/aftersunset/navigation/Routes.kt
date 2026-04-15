package com.example.aftersunset.navigation

import kotlinx.serialization.Serializable

/**
 * Grafo de navegación para el flujo de autenticación (Login y Registro).
 */
@Serializable object AuthGraph

/**
 * Grafo de navegación principal para la experiencia de usuario logueado.
 */
@Serializable object MainGraph

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
 */
@Serializable object Maps

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
 * @param id Identificador único del evento en Firebase.
 */
@Serializable data class EventDetail(val id: String)

/**
 * Pantalla de perfil de una discoteca u organizador.
 * @param id Identificador de la discoteca para mostrar sus reseñas y eventos propios.
 */
@Serializable data class VenueProfile(val id: String)

/**
 * Pantalla de pasarela de pago y confirmación de compra.
 * @param eventId ID del evento que el usuario está adquiriendo.
 */
@Serializable data class Checkout(val eventId: String)