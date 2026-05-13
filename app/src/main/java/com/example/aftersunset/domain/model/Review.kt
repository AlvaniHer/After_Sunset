package com.example.aftersunset.domain.model

/**
 * Representa una reseña u opinión de un usuario sobre un local.
 *
 * @property id ID único de la reseña.
 * @property venueId ID del local al que pertenece.
 * @property user Nombre del usuario.
 * @property rating Puntuación
 * @property comment Comentario o descripción de la experiencia.
 * @property userImage Imagen de acompañamiento del comentario
 */
data class Review(
    val id: String = "",
    val venueId: String = "",
    val userId: String = "",
    var user: String="",
    val rating: Int = 5,
    val comment: String = "",
    val userImage: String = ""
)
