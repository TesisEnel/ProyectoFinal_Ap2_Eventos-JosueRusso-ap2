package com.ucne.instantticket.ui.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.ui.RegistroEvento.EventoEvent
import com.ucne.instantticket.ui.RegistroEvento.EventoViewModel

@Composable
fun Home(
    viewModel: EventoViewModel = hiltViewModel(),
    onClickRegistro: (Int) -> Unit
) {
    val evento by viewModel.evento.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Mis Eventos",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn {
            items(evento) { evento ->
                ConsultaCard(
                    evento = evento,
                    onDeleteClick = { viewModel.onEvent(EventoEvent.onDelete(evento))}
                ) {
                    onClickRegistro(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ConsultaCard(
    evento: EventoEntity,
    onDeleteClick: () -> Unit,
    onClickRegistro: (Int) -> Unit

) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Nombre Evento: ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(evento.nombreEvento)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Fecha: ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(evento.fecha)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Descripcion: ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(evento.descripcion)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Recordatorio: ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(evento.recordatorio)
                            }
                        }
                    )
                    Row {

                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar"
                            )
                        }

                        IconButton(
                            onClick = {
                                onClickRegistro(evento.idevento)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar"
                            )
                        }

                    }

                }

            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                    text = {
                        Text("¿Estás seguro de eliminar este cliente?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteClick()
                                showDialog = false
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

