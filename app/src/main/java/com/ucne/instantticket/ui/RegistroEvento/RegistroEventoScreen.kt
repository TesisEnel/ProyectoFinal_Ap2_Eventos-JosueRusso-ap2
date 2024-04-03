package com.ucne.instantticket.ui.RegistroEvento

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEventoScreen(
    viewModel: EventoViewModel = hiltViewModel(),
    id: Int = 0,
    onclickHome: () -> Unit
) {
    if (id > 0)
        remember {
            viewModel.onEvent(EventoEvent.onSearch(id))
            0
        }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val evento = state.evento
    var datePickerState1 = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

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
                        text = "Registro de Evento",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = evento.nombreEvento,
                        onValueChange = { viewModel.onEvent(EventoEvent.nombreEvento(it)) },
                        label = { Text(text = "Nombre del Evento") },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    if (state.emptyFields.contains("Nombre Evento")) {
                        Text(text = "Nombre Evento es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = evento.descripcion,
                        onValueChange = { viewModel.onEvent(EventoEvent.descripcion(it)) },
                        label = { Text(text = "Descripcion") },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    if (state.emptyFields.contains("des")) {
                        Text(text = "La Descripcion es requerido", color = Color.Red)
                    }

                    //Fecha
                    DateInput(viewModel, datePickerState1)
                    if (state.emptyFields.contains("Fecha")) {
                        Text(text = "Fecha es requerido", color = Color.Red)
                    }

                    //Recordatorio
                    TimePickerInput(viewModel, timePickerState)
                    if (state.emptyFields.contains("recordatorio")) {
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
                                onclickHome.invoke()
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
                                datePickerState1.selectedDateMillis = null

                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "New"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    viewModel: EventoViewModel,
    datePickerState: DatePickerState = rememberDatePickerState()
) {
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isError = state.error != null || state.emptyFields.isNotEmpty()

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (datePickerState.selectedDateMillis != null) formatter.format(
                    Date(
                        datePickerState.selectedDateMillis!!
                    )
                ) else "",
                onValueChange = {},
                isError = isError,
                label = { Text(text = "Fecha") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier.clickable { showDialog = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Confirmar")
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
        ) {
            DatePicker(state = datePickerState)
        }
    }

    datePickerState.selectedDateMillis?.let { selectedDate ->
        val formattedDate = formatter.format(Date(selectedDate))
        viewModel.onEvent(EventoEvent.fecha(formattedDate))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerInput(viewModel: EventoViewModel, timePickerState: TimePickerState) {
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isError = state.error != null || state.emptyFields.isNotEmpty()

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        set(Calendar.MINUTE, timePickerState.minute)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = timeFormatter.format(calendar.time),
                onValueChange = {},
                label = { Text(text = "Hora Recordatorio") },
                isError = isError,
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Seleccionar hora",
                        modifier = Modifier.clickable { showDialog = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Seleccionar Hora", style = MaterialTheme.typography.headlineSmall)
                    TimePicker(state = timePickerState)
                    Row {
                        TextButton(
                            onClick = {
                                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                calendar.set(Calendar.MINUTE, timePickerState.minute)
                                viewModel.onEvent(
                                    EventoEvent.recordatorio(
                                        timeFormatter.format(
                                            calendar.time
                                        )
                                    )
                                )
                                showDialog = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                        TextButton(
                            onClick = { showDialog = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}








