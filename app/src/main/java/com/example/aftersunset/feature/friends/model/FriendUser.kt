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
    val fecha_registro: Timestamp? = null
)