package com.example.aftersunset.domain.model

import com.google.firebase.firestore.PropertyName

/**
 * Define los niveles de rango del sistema de fidelización.
 */
enum class UserLevel {
    STANDARD, VIP, GOLD, LEGENDARY
}

/**
 * Representa al usuario dentro de la plataforma.
 */
data class User(
    val id: String="",
    val username: String="",
    @get:PropertyName("nombre")
    @set:PropertyName("nombre")
    @field:PropertyName("nombre")
    var name: String="",
    val email: String="",
    val location: String="",
    val level: UserLevel= UserLevel.STANDARD,
    val points: Int=0,
    val eventsAttended: Int=0,
    val followingCount: Int=0,
    @get:PropertyName("foto_perfil")
    @set:PropertyName("foto_perfil")
    @field:PropertyName("foto_perfil")
    var profileImageUrl: String= "",
    val pendingLevelUp: Boolean = false
)