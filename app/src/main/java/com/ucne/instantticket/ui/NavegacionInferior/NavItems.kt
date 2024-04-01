package com.ucne.instantticket.ui.NavegacionInferior

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

class NavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItems(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.Home.name
    ),
    NavItems(
        label = "Evento",
        icon = Icons.Default.Create,
        route = Screens.RegistroEventoScreen.name + "/0"
    ),



)