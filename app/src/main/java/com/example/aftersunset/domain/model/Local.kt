package com.example.aftersunset.domain.model

data class Local(
    val id: Int,
    val nombre: String,
    val zona: String,
    val direccion: String,
    val aforo: Int,
    val edadMinima: Int,
    val fotoPrincipal: String,
    val descripcion: String
)