package com.example.aftersunset.domain.model

/**
 * Representa un local o establecimiento de ocio nocturno.
 * Contiene información física, técnica y descriptiva de la sala.
 *
 * @property id Identificador único del local.
 * @property name Nombre comercial de la sala.
 * @property zone Área geográfica donde se ubica .
 * @property address Dirección postal completa.
 * @property latitude Latitud para geolocalización.
 * @property longitude Longitud para geolocalización.
 * @property capacity Capacidad máxima de personas permitida.
 * @property minAge Edad mínima requerida para el acceso al local.
 * @property mainPhoto URL de la imagen representativa del establecimiento.
 * @property description Reseña detallada sobre el estilo y ambiente del local.
 */


data class Venue(
    val id: String="",
    val name: String="",
    val zone: String="",
    val address: String="",
    val latitude: Double=0.0,
    val longitude: Double=0.0,
    val capacity: Int=0,
    val minAge: Int=18,
    val mainPhoto: String="",
    val description: String=""
)