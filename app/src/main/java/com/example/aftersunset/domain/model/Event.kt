package com.example.aftersunset.domain.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

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
    val id: String = "",

    @get:PropertyName("id_local")
    @field:PropertyName("id_local")
    val venueId: String = "",

    @get:PropertyName("nombre_evento")
    @field:PropertyName("nombre_evento")
    val title: String = "",

    var clubName: String = "",
    var zone: String = "",
    var fullAddress: String = "",

    @get:PropertyName("fecha")
    @field:PropertyName("fecha")
    val dateTimestamp: Timestamp? = null,

    @get:PropertyName("precio_entrada")
    @field:PropertyName("precio_entrada")
    val price: Double = 0.0,

    @get:PropertyName("imagen_evento")
    @field:PropertyName("imagen_evento")
    val imageUrl: String = "",

    @get:PropertyName("generos")
    @field:PropertyName("generos")
    val genre: List<String> = emptyList(),

    @get:PropertyName("tags")
    @field:PropertyName("tags")
    val tags: List<String> = emptyList(),

    var latitude: Double = 0.0,
    var longitude: Double = 0.0,

    @get:PropertyName("descripcion")
    @field:PropertyName("descripcion")
    val description: String = "",

    @get:PropertyName("edad_minima")
    @field:PropertyName("edad_minima")
    val minAge: Int = 0,

    @get:PropertyName("edad_media_aprox")
    @field:PropertyName("edad_media_aprox")
    val avgAge: Int = 0,

    @get:PropertyName("aforo_evento")
    @field:PropertyName("aforo_evento")
    val capacity: Int = 0,

    ) {
    val date: String
        get() = dateTimestamp?.let {
            val sdf = SimpleDateFormat("EEEE, d MMMM", Locale("es", "ES"))
            sdf.format(it.toDate()).replaceFirstChar { it.uppercase() }
        } ?: "Fecha no disponible"

    constructor() : this(id = "")
}
