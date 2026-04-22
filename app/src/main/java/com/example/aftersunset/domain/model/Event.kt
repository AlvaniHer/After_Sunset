package com.example.aftersunset.domain.model

/**
 * Representa un evento de ocio nocturno en la plataforma.
 *
 * @property id Identificador único del evento.
 * @property venueId Identificador del local donde se celebra.
 * @property title Nombre del evento.
 * @property clubName Nombre del local para visualización rápida.
 * @property date Fecha formateada.
 * @property price Precio base.
 * @property imageUrl URL de la imagen promocional.
 * @property genre Género musical.
 * @property tags Etiquetas descriptivas.
 * @property zone Zona geográfica.
 * @property fullAddress Dirección completa.
 * @property latitude Latitud.
 * @property longitude Longitud.
 * @property description Reseña informativa.
 * @property minAge Edad mínima.
 * @property avgAge Edad media.
 * @property capacity Aforo total.
 * @property isSoldOut Indica si las entradas están agotadas.
 */
data class Event(
    val id: String,
    val venueId: String,
    val title: String,
    val clubName: String,
    val date: String,
    val price: Double,
    val imageUrl: String,
    val genre: String,
    val tags: List<String>,
    val zone: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val minAge: Int,
    val avgAge: Int,
    val capacity: Int,
    val isSoldOut: Boolean = false
)
