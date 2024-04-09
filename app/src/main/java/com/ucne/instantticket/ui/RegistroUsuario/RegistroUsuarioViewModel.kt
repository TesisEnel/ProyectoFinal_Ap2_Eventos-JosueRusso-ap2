package com.ucne.instantticket.ui.RegistroUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.instantticket.data.entity.UsuarioEntity
import com.ucne.instantticket.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroUsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UsuarioState())
    val state: StateFlow<UsuarioState> = _state.asStateFlow()
    val login: Flow<UsuarioEntity> = usuarioRepository.getUsuario()

    fun onEventUsario(event: UsuarioEvent) {
        when (event) {
            is UsuarioEvent.idUsuario -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(
                            idUsuario = event.idUsuario.toIntOrNull() ?: 0
                        )
                    )
                }
            }

            is UsuarioEvent.nombreCompleto -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(nombreCompleto = event.nombreCompleto)
                    )
                }
            }

            is UsuarioEvent.nombreUsuario -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(nombreUsuario = event.nombreUsuario)
                    )
                }
            }

            is UsuarioEvent.edad -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(edad = event.edad.toIntOrNull() ?: 0)
                    )
                }
            }

            is UsuarioEvent.fechaNacimiento -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(fechaNacimiento = event.fechaNacimiento)
                    )
                }
            }

            is UsuarioEvent.email -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(email = event.email)
                    )
                }
            }

            is UsuarioEvent.password -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(password = event.password)
                    )
                }
            }

            is UsuarioEvent.Imagen -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(imagen = event.imagen)
                    )
                }
            }

            is UsuarioEvent.isLogin -> {
                _state.update {
                    it.copy(
                        usario = it.usario.copy(islogin = event.isLogin.toBoolean())
                    )
                }
            }

            is UsuarioEvent.onSearch -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            usario = Buscar(event.id).first()
                        )
                    }
                }
            }

            is UsuarioEvent.onDelete -> {
                viewModelScope.launch {
                    usuarioRepository.delete(event.usurio)
                }
            }

            is UsuarioEvent.onUpdate -> {
                viewModelScope.launch {
                    usuarioRepository.update(event.usurio)
                }
            }

            UsuarioEvent.onSave -> {
                guardar()
            }

            UsuarioEvent.onNew -> {
                _state.update {
                    it.copy(
                        successMessage = null,
                        error = null,
                        usario = UsuarioEntity()
                    )
                }
            }

        }
    }

    fun Buscar(id: Int): Flow<UsuarioEntity> {
        return usuarioRepository.getUsuarioId(id)
    }


    private fun guardar() {
        val usuario = state.value.usario
        val nombreCompleto = usuario.nombreCompleto
        val nombreUsuario = usuario.nombreUsuario
        val edad = usuario.edad
        val fechaNacimiento = usuario.fechaNacimiento
        val email = usuario.email
        val password = usuario.password
        val imagen = usuario.imagen

        val emptyFields = mutableListOf<String>()
        if (nombreCompleto.isBlank()) {
            emptyFields.add("Nombre Completo")
        }
        if (nombreUsuario.isBlank()) {
            emptyFields.add("Nombre de Usuario")
        }
        if (edad <= 0) {
            emptyFields.add("Edad")
        }
        if (fechaNacimiento.isBlank()) {
            emptyFields.add("Fecha de Nacimiento")
        }
        if (email.isBlank()) {
            emptyFields.add("Email")
        }
        if (password.isBlank()) {
            emptyFields.add("Password")
        }

        if (imagen.isBlank()) {
            emptyFields.add("Imagen")
        }

        if (emptyFields.isNotEmpty()) {
            _state.update {
                it.copy(
                    error = "Llene los campos requeridos: ${emptyFields.joinToString(", ")}",
                    emptyFields = emptyFields
                )
            }
            return
        }

        val erroresPassword = validarPassword(password)
        if (erroresPassword.isNotEmpty()) {
            _state.update {
                it.copy(error = erroresPassword.joinToString(" \n"))
            }
            return
        }

        viewModelScope.launch {
            try {
                usuarioRepository.upsert(usuario)
                _state.update {
                    it.copy(
                        successMessage = "Se guardó correctamente",
                        error = null,
                        isLoading = false,
                        usario = UsuarioEntity(),
                        emptyFields = listOf()
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Ocurrió un error al guardar",
                        successMessage = null,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun validarPassword(password: String): List<String> {
        val errores = mutableListOf<String>()
        if (!Regex(".*[A-Z].*").containsMatchIn(password)) {
            errores.add("La contraseña debe contener al menos una letra mayúscula.")
        }
        if (!Regex(".*[0-9].*").containsMatchIn(password)) {
            errores.add("La contraseña debe contener al menos un número.")
        }
        if (!Regex(".*[!@#\$%^&*()-_+=|\\\\{}\\[\\]:\";'<>?,./].*").containsMatchIn(password)) {
            errores.add("La contraseña debe contener al menos un símbolo.")
        }
        if (password.length < 8) {
            errores.add("La contraseña debe tener al menos 8 caracteres.")
        }
        return errores
    }
}

data class UsuarioState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val usario: UsuarioEntity = UsuarioEntity(),
    val successMessage: String? = null,
    val emptyFields: List<String> = listOf()
)

sealed interface UsuarioEvent {
    data class idUsuario(val idUsuario: String) : UsuarioEvent
    data class nombreCompleto(val nombreCompleto: String) : UsuarioEvent
    data class nombreUsuario(val nombreUsuario: String) : UsuarioEvent
    data class edad(val edad: String) : UsuarioEvent
    data class fechaNacimiento(val fechaNacimiento: String) : UsuarioEvent
    data class email(val email: String) : UsuarioEvent
    data class password(val password: String) : UsuarioEvent
    data class Imagen(val imagen: String) : UsuarioEvent
    data class isLogin(val isLogin: String) : UsuarioEvent

    data class onUpdate(val usurio: UsuarioEntity) : UsuarioEvent
    data class onDelete(val usurio: UsuarioEntity) : UsuarioEvent

    data class onSearch(val id: Int) : UsuarioEvent

    object onSave : UsuarioEvent
    object onNew : UsuarioEvent
}