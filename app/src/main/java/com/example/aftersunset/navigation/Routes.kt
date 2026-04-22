package com.example.aftersunset.navigation

import kotlinx.serialization.Serializable

/**
 * Pantalla de carga inicial que se muestra al abrir la aplicación.
 */
@Serializable object Splash

/**
 * Grafo de navegación para el flujo de autenticación (Login y Registro).
 */
@Serializable object AuthGraph

/**
 * Grafo de navegación principal para la experiencia de usuario autenticado.
 */
@Serializable object MainGraph

/**
 * Punto de entrada principal para el flujo autenticado.
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
 * Pestaña de mapa interactivo para la geolocalización de eventos y salas.
 *
 * @param lat Latitud opcional para centrar la cámara en una ubicación específica.
 * @param lng Longitud opcional para centrar la cámara en una ubicación específica.
 */
@Serializable data class Maps(
    val lat: Double? = null,
    val lng: Double? = null
)

/**
 * Pestaña de gestión de entradas, donde el usuario visualiza sus pases activos.
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
 * Pantalla de pasarela de pago y confirmación de compra de entradas.
 *
 * @param eventId Identificador del evento relacionado con la transacción.
 * @param ticketType Categoría de la entrada seleccionada.
 * @param price Importe de la entrada antes de gastos de gestión.
*/
@Serializable data class Checkout(
    val eventId: String,
    val ticketType: String,
    val price: Double
)