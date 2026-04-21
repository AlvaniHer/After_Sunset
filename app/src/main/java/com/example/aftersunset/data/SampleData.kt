package com.example.aftersunset.data

import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.domain.model.Ticket
import com.example.aftersunset.domain.model.User
import com.example.aftersunset.domain.model.UserLevel

object SampleData {
    val sampleUser = User(
        id = "USER-001",
        name = "Álvaro Pérez",
        email = "alvaro.perez@email.com",
        location = "Málaga, ES",
        level = UserLevel.GOLD,
        points = 450,
        eventsAttended = 12,
        followingCount = 8,
        profileImageUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=Alvaro"
    )

    val sampleEvents = listOf(
        Event(
            id = "1",
            title = "Neon Ritual",
            clubName = "Sala Gold",
            date = "Viernes, 24 Mayo",
            price = 15.0,
            imageUrl = "https://picsum.photos/id/123/800/600",
            genre = "Techno",
            tags = listOf("Centro", "VIP", "Luces LED"),
            zone = "Málaga Centro",
            fullAddress = "C. Luis de Velázquez, 5, 29008 Málaga",
            latitude = 36.7218,
            longitude = -4.4185,
            description = "Vive la experiencia techno más exclusiva en el corazón de Málaga. Sonido Funktion-One y el mejor ambiente.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
        Event(
            id = "2",
            title = "Sunset Beats",
            clubName = "Moliere Playa",
            date = "Sábado, 25 Mayo",
            price = 20.0,
            imageUrl = "https://picsum.photos/id/158/800/600",
            genre = "House",
            tags = listOf("Beach Club", "Vistas al Mar", "Terraza"),
            zone = "Torremolinos / Los Álamos",
            fullAddress = "Paseo Marítimo Los Álamos, s/n, 29620 Torremolinos",
            latitude = 36.6436,
            longitude = -4.4674,
            description = "Baila bajo las estrellas frente al Mediterráneo. La fiesta comienza al atardecer y no para hasta el amanecer.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
        Event(
            id = "3",
            title = "Urban Jungle",
            clubName = "The Sound",
            date = "Jueves, 23 Mayo",
            price = 10.0,
            imageUrl = "https://picsum.photos/id/249/800/600",
            genre = "Reggaeton",
            tags = listOf("Universitario", "Barra Libre", "Teatinos"),
            zone = "Teatinos",
            fullAddress = "C. Plutarco, 58, 29010 Málaga",
            latitude = 36.7165,
            longitude = -4.4712,
            description = "La noche de los jueves en Teatinos tiene un nombre. Música urbana y los mejores cócteles de la zona.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        ),
        Event(
            id = "4",
            title = "Electronic Culture",
            clubName = "París 15",
            date = "Sábado, 1 Junio",
            price = 25.0,
            imageUrl = "https://picsum.photos/id/321/800/600",
            genre = " Hard Techno",
            tags = listOf("Concierto", "Aforo +3000", "Sonido Pro"),
            zone = "Polígono San Luis",
            fullAddress = "C. la Orotava, 27, 29006 Málaga",
            latitude = 36.7032,
            longitude = -4.4563,
            description = "El templo de la electrónica en el sur de España. Un despliegue de luces y sonido que te dejará sin aliento.",
            minAge = 21,
            avgAge = 26,
            capacity = 500,
            isSoldOut = false
        )
    )

    val sampleTickets = listOf(
        Ticket(
            id = "TKT-001",
            eventTitle = "Neon Ritual",
            clubName = "Sala Gold",
            date = "24 Mayo 2024",
            time = "23:30",
            entryType = "VIP Pass",
            price = 25.0,
            qrCodeData = "AS-GOLD-NEON-2024-VIP-001",
            imageUrl = "https://picsum.photos/id/123/400/400"
        ),
        Ticket(
            id = "TKT-002",
            eventTitle = "Sunset Beats",
            clubName = "Moliere Playa",
            date = "25 Mayo 2024",
            time = "18:00",
            entryType = "General + 1 Consumición",
            price = 20.0,
            qrCodeData = "AS-MOLIERE-SUNSET-2024-GEN-002",
            imageUrl = "https://picsum.photos/id/158/400/400"
        ),
        Ticket(
            id = "TKT-003",
            eventTitle = "Urban Jungle",
            clubName = "The Sound",
            date = "23 Mayo 2024",
            time = "23:00",
            entryType = "Anticipada",
            price = 12.0,
            qrCodeData = "AS-SOUND-URBAN-2024-EARLY-003",
            imageUrl = "https://picsum.photos/id/249/400/400"
        ),
        Ticket(
            id = "TKT-004",
            eventTitle = "Electronic Culture",
            clubName = "París 15",
            date = "01 Junio 2024",
            time = "22:00",
            entryType = "Entrada General",
            price = 25.0,
            qrCodeData = "AS-P15-ELEC-2024-GEN-004",
            imageUrl = "https://picsum.photos/id/321/400/400"
        )
    )
}
