package com.example.aftersunset.ui.screens.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aftersunset.R
import com.example.aftersunset.domain.model.Event
import com.example.aftersunset.ui.components.map.EventMapCard
import com.example.aftersunset.ui.components.map.ZoneSelector
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

/**
 * Pantalla de Mapa interactivo.
 * Visualiza los eventos geolocalizados en Málaga, permitiendo filtrar por zonas
 * y acceder al detalle de cada evento. Soporta el auto-centrado mediante coordenadas externas.
 *
 * @param events Lista de objetos [Event] a posicionar en el mapa.
 * @param onEventClick Callback para navegar al detalle de un evento seleccionado.
 * @param initialLat Latitud inicial opcional para centrar la cámara al abrir la pantalla.
 * @param initialLng Longitud inicial opcional para centrar la cámara al abrir la pantalla.
 */
@Composable
fun MapScreen(
    events: List<Event>,
    onEventClick: (String) -> Unit,
    initialLat: Double? = null,
    initialLng: Double? = null
) {
    val malagaBounds = LatLngBounds(
        LatLng(36.60, -4.60),
        LatLng(36.80, -4.20)
    )

    val mapProperties = MapProperties(
        latLngBoundsForCameraTarget = malagaBounds,
        minZoomPreference = 11f,
        maxZoomPreference = 18f,
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.map_style)
    )

    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var currentSelectedZone by remember { mutableStateOf("Todos") }

    val filteredEvents by remember(currentSelectedZone, events) {
        derivedStateOf {
            if (currentSelectedZone == "Todos") {
                events
            } else {
                events.filter { it.zone.contains(currentSelectedZone, ignoreCase = true) }
            }
        }
    }

    var eventToDisplay by remember { mutableStateOf<Event?>(null) }
    if (selectedEvent != null) {
        eventToDisplay = selectedEvent
    }

    val malagaCenter = LatLng(36.7213, -4.4214)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(malagaCenter, 13f)
    }

    LaunchedEffect(initialLat, initialLng) {
        if (initialLat != null && initialLng != null) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(initialLat, initialLng), 16f),
                durationMs = 1000
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = { selectedEvent = null },
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            filteredEvents.forEach { event ->
                val markerColor = when {
                    event.genre.contains("Techno", true) -> BitmapDescriptorFactory.HUE_VIOLET
                    event.genre.contains("Reggaeton", true) -> BitmapDescriptorFactory.HUE_MAGENTA
                    event.genre.contains("House", true) -> BitmapDescriptorFactory.HUE_CYAN
                    else -> BitmapDescriptorFactory.HUE_GREEN
                }
                Marker(
                    state = MarkerState(position = LatLng(event.latitude, event.longitude)),
                    title = event.title,
                    snippet = "${event.clubName} - ${event.price}€",
                    onClick = {
                        selectedEvent = event
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(markerColor)
                )
            }
        }

        ZoneSelector(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            onZoneSelected = { zone ->
                currentSelectedZone = zone
                selectedEvent = null
            }
        )

        AnimatedVisibility(
            visible = selectedEvent != null,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500)),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            eventToDisplay?.let { event ->
                EventMapCard(
                    event = event,
                    onDetailClick = { onEventClick(event.id) }
                )
            }
        }
    }
}
