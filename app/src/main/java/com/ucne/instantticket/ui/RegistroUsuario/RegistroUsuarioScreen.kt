package com.ucne.instantticket.ui.RegistroUsuario

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.instantticket.ui.theme.InstantTicketTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RegistroUsuarioScreen(viewModel: RegistroUsuarioViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val usuario = state.usario
    val isError = state.error != null || state.emptyFields.isNotEmpty()
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(390.dp)
                .height(625.dp),
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Registro de Usuario",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = usuario.nombreCompleto,
                        onValueChange = { viewModel.onEvent(UsuarioEvent.nombreCompleto(it)) },
                        isError = isError,
                        label = { Text(text = "Nombre Completo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp))
                    if (state.emptyFields.contains("Nombre Completo")) {
                        Text(text = "Nombre Completo es requerido", color = Color.Red, )
                    }

                    OutlinedTextField(
                        value = usuario.nombreUsuario,
                        onValueChange = { viewModel.onEvent(UsuarioEvent.nombreUsuario(it)) },
                        label = { Text(text = "Nombre de Usuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Nombre de Usuario")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp))
                    )
                    if (state.emptyFields.contains("Nombre de Usuario")) {
                        Text(text = "Nombre de Usuario es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.edad.toString(),
                        onValueChange = { viewModel.onEvent(UsuarioEvent.edad(it)) },
                        label = { Text(text = "Edad") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Edad")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (state.emptyFields.contains("Edad")) {
                        Text(text = "Edad es requerido", color = Color.Red)
                    }


                    OutlinedTextField(
                        value = usuario.fechaNacimiento,
                        onValueChange = { viewModel.onEvent(UsuarioEvent.fechaNacimiento(it)) },
                        label = { Text(text = "Fecha de Nacimiento") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp).border(1.dp, if (state.emptyFields.contains("Fecha de Nacimiento")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (state.emptyFields.contains("Fecha de Nacimiento")) {
                        Text(text = "Fecha de Nacimiento es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.email,
                        onValueChange = { viewModel.onEvent(UsuarioEvent.email(it)) },
                        label = { Text(text = "Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Email")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp))
                    )
                    if (state.emptyFields.contains("Email")) {
                        Text(text = "Email es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.password,
                        onValueChange = { viewModel.onEvent(UsuarioEvent.password(it)) },
                        label = { Text(text = "Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Password")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp))
                    )

                    if (state.emptyFields.contains("Password")) {
                        Text(text = "Password es requerido", color = Color.Red)
                    }

                    val errorMessages = state.error
                    if (!errorMessages.isNullOrEmpty()) {
                        errorMessages.split(" \n").forEach { error ->
                            Text(text = error, color = Color.Red)
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(UsuarioEvent.onSave)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Save")
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.onEvent(UsuarioEvent.onNew)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = "New")
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


