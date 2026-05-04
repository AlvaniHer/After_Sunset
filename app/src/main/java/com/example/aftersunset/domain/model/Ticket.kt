package com.example.aftersunset.domain.model

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
    val id: String,
    val eventId: String,
    val eventTitle: String,
    val clubName: String,
    val date: String,
    val time: String,
    val entryType: String,
    val price: Double,
    val qrCodeData: String,
    val imageUrl: String
)
