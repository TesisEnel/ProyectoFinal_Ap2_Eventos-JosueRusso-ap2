package com.ucne.instantticket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.ucne.instantticket.data.entity.UsuarioEntity
import com.ucne.instantticket.data.repository.UsuarioRepository
import com.ucne.instantticket.ui.Login.LoginScreen
import com.ucne.instantticket.ui.NavegacionInferior.Nav
import com.ucne.instantticket.ui.NavegacionInferior.Screens
import com.ucne.instantticket.ui.RegistroUsuario.RegistroUsuarioViewModel
import com.ucne.instantticket.ui.theme.InstantTicketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstantTicketTheme {

                MenuPrincipal(viewModel = viewModel())
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(viewModel: RegistroUsuarioViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "EventoMaster")
                },
                actions = {

                    // Obtener imagen del usuario del ViewModel
                   /* val imagenUsuario by viewModel.imagenUsuario.collectAsState()
                    Image(
                        painter = rememberImagePainter(data = imagenUsuario),
                        contentDescription = "Imagen de perfil",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                navController.navigate(Screens.PerfilUsuario.name) {
                                    // Pasar datos del usuario a PerfilUsuario
                                    launchSingleTop = true
                                    popUpTo(Screens.Home.name) { inclusive = false }
                                }
                            }
                    )*/
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.LoginScreen.name)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "LoginOut"
                        )
                    }
                }
            )
        }
    ){
        Nav(navController = navController)
    }
}


