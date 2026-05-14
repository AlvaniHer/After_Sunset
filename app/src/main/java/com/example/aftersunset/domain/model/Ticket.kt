package com.example.aftersunset.domain.model

import com.google.firebase.firestore.PropertyName

/**
 * Representa una entrada adquirida por un usuario para un evento.
 * Vincula al usuario con el evento y contiene la información necesaria para la validación de acceso.
 *
 * @property id Identificador único del ticket.
 * @property eventId Referencia al identificador único del evento original.
 * @property eventTitle Título del evento para visualización rápida.
 * @property clubName Nombre del local donde se realiza el evento.
 * @property date Fecha del evento.
 * @property time Hora de apertura de puertas o inicio del evento.
 * @property entryType Categoría de la entrada.
 * @property price Precio pagado por la entrada.
 * @property qrCodeData Cadena de texto cifrada o identificador para la generación del código QR de acceso.
 * @property imageUrl URL de la imagen promocional o del local asociada al ticket.
 */
data class Ticket(
    val id: String = "",

    @get:PropertyName("id_usuario") @set:PropertyName("id_usuario")
    var userId: String = "",

    @get:PropertyName("id_evento") @set:PropertyName("id_evento")
    var eventId: String = "",

    @get:PropertyName("imageUrl") @set:PropertyName("imageUrl")
    var imageUrl: String = "",

    @get:PropertyName("titulo_evento") @set:PropertyName("titulo_evento")
    var eventTitle: String = "",

    @get:PropertyName("nombre_local") @set:PropertyName("nombre_local")
    var clubName: String = "",

    @get:PropertyName("tipo_entrada") @set:PropertyName("tipo_entrada")
    var ticketType: String = "",

    @get:PropertyName("precio_total") @set:PropertyName("precio_total")
    var price: Double = 0.0,

    @get:PropertyName("codigo_qr") @set:PropertyName("codigo_qr")
    var qrCodeData: String = "",

    @get:PropertyName("estado_entrada") @set:PropertyName("estado_entrada")
    var estado_entrada: String = "",

    @get:PropertyName("fecha_compra") @set:PropertyName("fecha_compra")
    var purchaseDate: com.google.firebase.Timestamp? = null
)