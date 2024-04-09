package com.ucne.instantticket.ui.NavegacionInferior

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.ucne.instantticket.ui.Home.Home
import com.ucne.instantticket.ui.Login.LoginScreen
import com.ucne.instantticket.ui.RegistroEvento.RegistroEventoScreen
import com.ucne.instantticket.ui.RegistroUsuario.RegistroUsuarioScreen

@Composable
fun Nav(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        }, label = {
                            Text(text = navItem.label)
                        })
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.LoginScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.Home.name) {
                Home {
                    navController.navigate(Screens.RegistroEventoScreen.name + "/$it")
                }
            }
            composable(
                route = Screens.RegistroEventoScreen.name + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                RegistroEventoScreen(
                    id = id,
                    onclickHome = { navController.navigate(Screens.Home.name) }
                )
            }

            composable(route = Screens.LoginScreen.name) {
                LoginScreen(
                    onclickHome = { navController.navigate(Screens.Home.name) },
                    onclickRegistro = { navController.navigate(Screens.RegistroUsuarioScreen.name) }
                )

            }
            composable(route = Screens.RegistroUsuarioScreen.name) {

                RegistroUsuarioScreen(onclickBack = { navController.navigateUp() })

            }
        }
    }
}

