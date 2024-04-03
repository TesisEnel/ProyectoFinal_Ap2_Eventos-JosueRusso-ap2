package com.ucne.instantticket.ui.RegistroUsuario

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.instantticket.FileUtil
import com.ucne.instantticket.R
import com.ucne.instantticket.ui.RegistroEvento.EventoEvent
import com.ucne.instantticket.ui.RegistroEvento.EventoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    viewModel: RegistroUsuarioViewModel = hiltViewModel(),
    onclickBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val usuario = state.usario
    val context = LocalContext.current
    val isError = state.error != null || state.emptyFields.isNotEmpty()
    var passwordVisible by remember { mutableStateOf(false) }
    var datePickerState = rememberDatePickerState()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val filePath = uri?.let { FileUtil.saveImage(context, it) }
            filePath?.let { UsuarioEvent.Imagen(it) }?.let { viewModel.onEventUsario(it) }
        }

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
                        onValueChange = { viewModel.onEventUsario(UsuarioEvent.nombreCompleto(it)) },
                        isError = isError,
                        label = { Text(text = "Nombre Completo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    if (state.emptyFields.contains("Nombre Completo")) {
                        Text(text = "Nombre Completo es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.nombreUsuario,
                        onValueChange = { viewModel.onEventUsario(UsuarioEvent.nombreUsuario(it)) },
                        label = { Text(text = "Nombre de Usuario") },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    if (state.emptyFields.contains("Nombre de Usuario")) {
                        Text(text = "Nombre de Usuario es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.edad.toString(),
                        onValueChange = { viewModel.onEventUsario(UsuarioEvent.edad(it)) },
                        label = { Text(text = "Edad") },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (state.emptyFields.contains("Edad")) {
                        Text(text = "Edad es requerido", color = Color.Red)
                    }


                    FechaNacimiento(viewModel, datePickerState)
                    if (state.emptyFields.contains("Fecha de Nacimiento")) {
                        Text(text = "Fecha de Nacimiento es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.email,
                        onValueChange = { viewModel.onEventUsario(UsuarioEvent.email(it)) },
                        label = { Text(text = "Email") },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    if (state.emptyFields.contains("Email")) {
                        Text(text = "Email es requerido", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = usuario.password,
                        onValueChange = { viewModel.onEventUsario(UsuarioEvent.password(it)) },
                        label = { Text(text = "Password") },
                        isError = isError,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility) else painterResource(id = R.drawable.ic_visibility_off)
                            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = image, contentDescription = description)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )

                    if (state.emptyFields.contains("Password")) {
                        Text(text = "Password es requerido", color = Color.Red)
                    }



                    Surface(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(50.dp),
                        color = if (isError && usuario.imagen.isEmpty()) Color.Red else Color.Gray
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar Imagen",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            Text(
                                "Agregar Imagen de Perfil",
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    if (isError && usuario.imagen.isEmpty()) {
                        Text(text = "La imagen es requerida", color = Color.Red)
                    }

                    val errorMessages = state.error
                    val dialogVisible = remember { mutableStateOf(false) }


                    LaunchedEffect(errorMessages) {
                        if (!errorMessages.isNullOrEmpty()) {
                            dialogVisible.value = true
                        }
                    }


                    if (dialogVisible.value) {
                        AlertDialog(
                            onDismissRequest = {
                                dialogVisible.value = false
                            },
                            title = {
                                Text(
                                    text = "Error",
                                    color = Color.Red
                                )
                            },
                            text = {
                                Column {
                                    errorMessages?.split(" \n")?.forEach { error ->
                                        Text(
                                            text = error,
                                            color = Color.Red
                                        )
                                    }
                                }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        dialogVisible.value = false
                                    }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEventUsario(UsuarioEvent.onSave)
                                if (state.emptyFields.contains("Nombre Completo") &&
                                    state.emptyFields.contains("Nombre de Usuario") &&
                                    state.emptyFields.contains("Edad") &&
                                    state.emptyFields.contains("Email")&&
                                    state.emptyFields.contains("Password") &&
                                    isError && usuario.imagen.isEmpty()
                                )
                                    onclickBack.invoke()


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
                                viewModel.onEventUsario(UsuarioEvent.onNew)
                                datePickerState.selectedDateMillis = null
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
fun FechaNacimiento(viewModel: RegistroUsuarioViewModel, datePickerState : DatePickerState = rememberDatePickerState()) {
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isError = state.error != null || state.emptyFields.isNotEmpty()

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    Box(
        modifier = Modifier
               .fillMaxSize(),
        contentAlignment = Alignment.Center
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
                label = { Text(text = "Fecha de Nacimiento") },
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
        viewModel.onEventUsario(UsuarioEvent.fechaNacimiento(formattedDate))
    }
}

