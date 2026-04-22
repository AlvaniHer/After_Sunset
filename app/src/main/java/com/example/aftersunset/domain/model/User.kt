package com.example.aftersunset.domain.model

/**
 * Define los niveles de rango del sistema de fidelización.
 */
enum class UserLevel {
    STANDARD, VIP, GOLD, LEGENDARY
}

/**
 * Representa al usuario dentro de la plataforma.
 * Contiene información de perfil, estadísticas de actividad y estado de fidelización.
 *
 * @property id Identificador único del usuario.
 * @property name Nombre completo o alias del usuario.
 * @property email Correo electrónico de contacto.
 * @property location Ciudad o zona de residencia.
 * @property level Rango actual.
 * @property points Puntos acumulados por la compra y validación de entradas.
 * @property eventsAttended Número total de eventos a los que ha asistido el usuario.
 * @property followingCount Número de locales o clubes que el usuario sigue.
 * @property profileImageUrl URL de la imagen de avatar del usuario.
 * @property pendingLevelUp Flag que indica si hay una subida de nivel pendiente de celebrar en la UI.
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val location: String,
    val level: UserLevel,
    val points: Int,
    val eventsAttended: Int,
    val followingCount: Int,
    val profileImageUrl: String,
    val pendingLevelUp: Boolean = false
)
