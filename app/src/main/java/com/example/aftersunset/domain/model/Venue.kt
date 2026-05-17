package com.example.aftersunset.domain.model



import com.google.firebase.firestore.PropertyName

data class Venue(

    val id: String = "",

    @get:PropertyName("nombre")
    @set:PropertyName("nombre")
    var name: String = "",

    @get:PropertyName("tipo_local")
    @set:PropertyName("tipo_local")
    var zone: String = "",

    @get:PropertyName("direccion")
    @set:PropertyName("direccion")
    var address: String = "",

    @get:PropertyName("latitud")
    @set:PropertyName("latitud")
    var latitude: Double = 0.0,

    @get:PropertyName("longitud")
    @set:PropertyName("longitud")
    var longitude: Double = 0.0,

    @get:PropertyName("aforo")
    @set:PropertyName("aforo")
    var capacity: Int = 0,

    @get:PropertyName("edad_minima")
    @set:PropertyName("edad_minima")
    var minAge: Int = 18,

    @get:PropertyName("foto_principal")
    @set:PropertyName("foto_principal")
    var mainPhoto: String = "",

    @get:PropertyName("descripcion")
    @set:PropertyName("descripcion")
    var description: String = ""
)