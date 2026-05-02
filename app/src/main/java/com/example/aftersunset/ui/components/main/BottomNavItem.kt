package com.example.aftersunset.ui.components.main

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.AfterSunsetTheme

/**
 * Elemento individual de la barra de navegación.
 */
@Composable
fun BottomNavItem(
    icon: ImageVector? = null,
    painter: Int? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) AfterSunsetTheme.colors.secondary else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(if (isSelected) 30.dp else 24.dp)
            )
        } else if (painter != null) {
            Icon(
                painter = painterResource(id = painter),
                contentDescription = null,
                tint = if (isSelected) AfterSunsetTheme.colors.secondary else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(if (isSelected) 30.dp else 24.dp)
            )
        }
    }
}