package com.example.aftersunset.domain.model

/**
 * Representa un evento en la plataforma.
 */
data class Event(
    val id: String,
    val title: String,
    val clubName: String,
    val date: String,
    val price: Double,
    val imageUrl: String,
    val location: String,
    val tag: String
)