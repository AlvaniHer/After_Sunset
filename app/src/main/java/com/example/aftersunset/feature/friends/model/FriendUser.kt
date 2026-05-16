package com.example.aftersunset.feature.friends.model

import com.google.firebase.Timestamp

data class FriendUser(
    val nombre: String = "",
    val apellidos: String = "",
    val email: String = "",
    val username: String = "",
    val foto_perfil: String = "",
    val estado_cuenta: String = "",
    val fecha_nacimiento: Timestamp? = null,
    val fecha_registro: Timestamp? = null,
    val id: String = "",
    val name: String = "",
    val profileImageUrl: String = "",
    val location: String = "",
    val level: String = "",
    val points: Int = 0,
    val followingCount: Int = 0,
    val eventsAttended: Int = 0,
    val pendingLevelUp: Boolean = false
) {
    fun displayName(): String {
        val legacyName = listOf(nombre, apellidos)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .trim()

        return when {
            legacyName.isNotBlank() -> legacyName
            name.isNotBlank() -> name
            email.isNotBlank() -> email.substringBefore("@")
            else -> "Usuario"
        }
    }

    fun displayUsername(): String {
        return when {
            username.isNotBlank() -> "@$username"
            email.isNotBlank() -> "@${email.substringBefore("@")}"
            name.isNotBlank() -> "@${name.lowercase().replace(" ", "")}"
            nombre.isNotBlank() -> "@${nombre.lowercase().replace(" ", "")}"
            else -> "@usuario"
        }
    }

    fun displayAvatarUrl(): String {
        val image = when {
            foto_perfil.isNotBlank() -> foto_perfil
            profileImageUrl.isNotBlank() -> profileImageUrl
            else -> null
        }
        val seed = displayName().split(" ").firstOrNull() ?: "Usuario"
        return image ?: "https://api.dicebear.com/9.x/bottts-neutral/svg?seed=$seed"
    }
}