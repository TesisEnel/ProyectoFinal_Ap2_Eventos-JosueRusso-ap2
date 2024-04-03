package com.ucne.instantticket.ui.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.instantticket.R
import com.ucne.instantticket.data.entity.UsuarioEntity
import com.ucne.instantticket.ui.RegistroUsuario.RegistroUsuarioViewModel
import com.ucne.instantticket.ui.RegistroUsuario.UsuarioEvent


@Composable
fun LoginScreen(
    onclickHome: () -> Unit,
    onclickRegistro: () -> Unit
) {
    val viewModel: RegistroUsuarioViewModel = hiltViewModel()
    val usuario by viewModel.login.collectAsState(initial = UsuarioEntity())
    if (usuario != null)
        if (usuario.islogin)
            onclickHome.invoke()
        else
            LoginUi(onclickHome, onclickRegistro, usuario, viewModel)
    else
        LoginUi(onclickHome, onclickRegistro, usuario, viewModel)
}

@Composable
private fun LoginUi(
    onclickHome: () -> Unit,
    onclickRegistro: () -> Unit,
    usuario: UsuarioEntity,
    viewModel: RegistroUsuarioViewModel
) {
    var nombreUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nombreUsuario,
            onValueChange = { nombreUsuario = it },
            label = { Text("Nombre de Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) painterResource(id = R.drawable.ic_visibility) else painterResource(
                        id = R.drawable.ic_visibility_off
                    )
                val description =
                    if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (usuario != null)
                    if (usuario.nombreUsuario == nombreUsuario && usuario.password == password) {
                        viewModel.onEventUsario(UsuarioEvent.isLogin(true.toString()))
                        viewModel.onEventUsario(UsuarioEvent.onUpdate(usuario))
                        onclickHome.invoke()

                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¿No tienes una cuenta?")
            TextButton(onClick = onclickRegistro) {
                Text("Regístrate", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}
