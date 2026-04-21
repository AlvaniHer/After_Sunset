package com.example.aftersunset.domain.model

data class Ticket(
    val id: String,
    val eventTitle: String,
    val clubName: String,
    val date: String,
    val time: String,
    val entryType: String,
    val price: Double,
    val qrCodeData: String,
    val imageUrl: String
)