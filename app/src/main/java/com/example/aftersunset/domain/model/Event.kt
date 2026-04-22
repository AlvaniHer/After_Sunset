package com.example.aftersunset.domain.model

/**
 * Representa un evento de ocio nocturno en la plataforma.
 * Contiene toda la información técnica y promocional necesaria para su visualización y gestión.
 *
 * @property id Identificador único del evento.
 * @property title Nombre del evento.
 * @property clubName Nombre del local donde se celebra el evento.
 * @property date Fecha formateada para visualización.
 * @property price Precio base de la entrada.
 * @property imageUrl URL de la imagen promocional del evento.
 * @property genre Género musical principal.
 * @property tags Etiquetas descriptivas.
 * @property zone Zona geográfica de Málaga.
 * @property fullAddress Dirección completa del local.
 * @property latitude Latitud para geolocalización en el mapa.
 * @property longitude Longitud para geolocalización en el mapa.
 * @property description Breve descripción informativa del evento.
 * @property minAge Edad mínima permitida para el acceso.
 * @property avgAge Edad media estimada del público asistente.
 * @property capacity Aforo total del evento.
 * @property isSoldOut Indica si las entradas están agotadas.
 */
data class Event(
    val id: String,
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
