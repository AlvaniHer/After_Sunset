package com.example.aftersunset.domain.model

enum class UserLevel {
    STANDARD, VIP, GOLD, LEGENDARY
}

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
