package com.example.aftersunset.domain.model

/**
 * Representa un local o establecimiento de ocio nocturno.
 * Contiene información física, técnica y descriptiva de la sala.
 *
 * @property id Identificador único del local.
 * @property nombre Nombre comercial de la sala.
 * @property zona Área geográfica donde se ubica .
 * @property direccion Dirección postal completa.
 * @property aforo Capacidad máxima de personas permitida.
 * @property edadMinima Edad mínima requerida para el acceso al local.
 * @property fotoPrincipal URL de la imagen representativa del establecimiento.
 * @property descripcion Reseña detallada sobre el estilo y ambiente del local.
 */
data class Local(
    val id: Int,
    val nombre: String,
    val zona: String,
    val direccion: String,
    val aforo: Int,
    val edadMinima: Int,
    val fotoPrincipal: String,
    val descripcion: String
)
