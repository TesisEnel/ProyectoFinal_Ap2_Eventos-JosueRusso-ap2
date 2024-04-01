package com.ucne.instantticket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucne.instantticket.ui.NavegacionInferior.Nav
import com.ucne.instantticket.ui.theme.InstantTicketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstantTicketTheme {
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
                                        navController.navigate("usuario")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "usuario"
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navController.navigate("login")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddCircle,
                                        contentDescription = "Login"
                                    )
                                }
                            }
                        )
                    }

                ){
                    Nav(navController = navController)
                }
            }
        }
    }
}

