package com.example.aftersunset.feature.friends.model

import com.google.firebase.Timestamp

data class Friendship(
    val id_usuario_emisor: String = "",
    val id_usuario_receptor: String = "",
    val estado_amistad: String = "",
    val fecha_solicitud: Timestamp? = null,
    val fecha_respuesta: Timestamp? = null
)