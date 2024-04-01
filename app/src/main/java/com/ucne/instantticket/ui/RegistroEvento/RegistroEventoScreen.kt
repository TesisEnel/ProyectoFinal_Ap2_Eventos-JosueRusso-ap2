package com.ucne.instantticket.ui.RegistroEvento

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.instantticket.data.entity.EventoEntity

@Composable
fun RegistroEventoScreen(viewModel: EventoViewModel = hiltViewModel(), id: Int = 0 ) {

    remember {
        viewModel.onEvent(EventoEvent.onSearch(id))
        0
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val evento = state.evento

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
                        text = "Registro de Evento",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = evento.nombreEvento,
                        onValueChange = { viewModel.onEvent(EventoEvent.nombreEvento(it)) },
                        isError = true,
                        label = { Text(text = "Nombre del Evento") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp))
                    if (state.emptyFields.contains("Nombre Evento")) {
                        Text(text = "Nombre Evento es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = evento.fecha,
                        onValueChange = { viewModel.onEvent(EventoEvent.fecha(it)) },
                        label = { Text(text = "Fecha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Fecha")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (state.emptyFields.contains("Fecha")) {
                        Text(text = "Fecha es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = evento.descripcion,
                        onValueChange = { viewModel.onEvent(EventoEvent.descripcion(it)) },
                        label = { Text(text = "Descripcion") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, if (state.emptyFields.contains("Descripcion")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp))
                    )
                    if (state.emptyFields.contains("Descripcion")) {
                        Text(text = "Descripcion es requerido", color = Color.Red)
                    }


                    OutlinedTextField(
                        value = evento.recordatorio,
                        onValueChange = { viewModel.onEvent(EventoEvent.recordatorio(it)) },
                        label = { Text(text = "Recordatorio") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp).border(1.dp, if (state.emptyFields.contains("Recordatorio")) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (state.emptyFields.contains("Recordatorio")) {
                        Text(text = "Recordatorio es requerido", color = Color.Red)
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(EventoEvent.onSave)
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
                                viewModel.onEvent(EventoEvent.onNew)
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
