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
    val genre: String,
    val tags: List<String>,
    val zone: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
)