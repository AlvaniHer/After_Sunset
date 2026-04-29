package com.example.aftersunset.domain.model

/**
 * Representa una reseña u opinión de un usuario sobre un local.
 *
 * @property user Nombre del usuario que realiza la reseña.
 * @property rating Puntuación otorgada.
 * @property comment Comentario o descripción de la experiencia.
 */
data class Review(
    val user: String,
    val rating: Int,
    val comment: String
)
