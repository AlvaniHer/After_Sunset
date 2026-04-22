package com.example.aftersunset.ui.components.profile

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aftersunset.ui.theme.PacificCyan

/**
 * Representa una opción interactiva dentro del menú de la pantalla de perfil.
 * Permite mostrar un icono, una etiqueta descriptiva y una flecha indicadora de navegación.
 *
 * @param icon Icono opcional de tipo [ImageVector].
 * @param painter ID de recurso opcional para iconos de tipo drawable.
 * @param label Texto descriptivo de la opción del menú.
 * @param onClick Acción a ejecutar cuando el usuario pulsa sobre la opción.
 */
@Composable
fun ProfileMenuItem(
    icon: ImageVector? = null,
    painter: Int? = null,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null){
                Icon(
                    icon,
                    null,
                    tint = PacificCyan,
                    modifier = Modifier.size(24.dp)
                )
            } else if (painter != null){
                Icon(
                    painterResource(id = painter),
                    null,
                    tint = PacificCyan,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(label,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                null,
                tint = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

@Preview
@Composable
fun ProfileMenuItemPreview(){
    ProfileMenuItem(
        icon = Icons.Default.Favorite,
        label = "Clubes Favoritos",
        onClick = {}
    )
}
