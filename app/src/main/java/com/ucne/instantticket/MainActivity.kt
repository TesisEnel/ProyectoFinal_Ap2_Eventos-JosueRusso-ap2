package com.ucne.instantticket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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
                val workRequest = OneTimeWorkRequestBuilder<NotificationService>()
                    .build()

                WorkManager.getInstance(this).enqueue(workRequest)
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

                    IconButton(
                        onClick = {
                            navController.navigate(Screens.LoginScreen.name)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "LoginOut"
                        )
                    }
                }
            )
        }
    ) {
        Nav(navController = navController)
    }
}


