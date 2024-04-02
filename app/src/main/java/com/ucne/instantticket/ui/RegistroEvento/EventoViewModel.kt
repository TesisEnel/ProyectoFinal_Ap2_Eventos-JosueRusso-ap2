package com.ucne.instantticket.ui.RegistroEvento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.data.repository.EventoRepository
import com.ucne.instantticket.ui.RegistroUsuario.UsuarioEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventoViewModel @Inject constructor(
    private val eventoRepository : EventoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EventoState())
    val state : StateFlow<EventoState> = _state.asStateFlow()
    val evento: Flow<List<EventoEntity>> = eventoRepository.getEvento()

    fun onEvent(event: EventoEvent) {
        when (event) {
            is EventoEvent.idevento -> {
                _state.update {
                    it.copy(
                        evento = it.evento.copy(
                            idevento = event.idevento.toIntOrNull() ?: 0
                        )
                    )
                }
            }

            is EventoEvent.nombreEvento -> {
                _state.update {
                    it.copy(
                        evento = it.evento.copy(nombreEvento = event.nombreEvento)
                    )
                }
            }

            is EventoEvent.fecha -> {
                _state.update {
                    it.copy(
                        evento = it.evento.copy(fecha = event.fecha)
                    )
                }
            }

            is EventoEvent.descripcion -> {
                _state.update {
                    it.copy(
                        evento = it.evento.copy(descripcion = event.descripcion)
                    )
                }
            }

            is EventoEvent.recordatorio -> {
                _state.update {
                    it.copy(
                        evento = it.evento.copy(recordatorio = event.recordatorio)
                    )
                }
            }



            EventoEvent.onSave -> {
                guardarEvento()
            }

            EventoEvent.onNew -> {
                _state.update {
                    it.copy(
                        successMessage = null,
                        error = null,
                        evento = EventoEntity()
                    )
                }
            }


            is EventoEvent.onDelete -> {
                viewModelScope.launch {
                    eventoRepository.delete(event.evento)
                }
            }

            is EventoEvent.onSearch ->{
                viewModelScope.launch{
                    _state.update {
                        it.copy(
                            evento = Buscar(event.id).first()
                        )
                    }
                }
            }
        }
    }
    fun  Buscar(id: Int): Flow<EventoEntity>{
        return eventoRepository.getEventoId(id)
    }

    private fun guardarEvento() {
        val evento = state.value.evento
        val nombreEvento = evento.nombreEvento
        val fecha = evento.fecha
        val descripcion = evento.descripcion
        val recordatorio = evento.recordatorio


        val emptyFields = mutableListOf<String>()
        if (nombreEvento.isBlank()) {
            emptyFields.add("Nombre Evento")
        }
        if (fecha.isBlank()) {
            emptyFields.add("Fecha")
        }
        if (descripcion.isBlank()) {
            emptyFields.add("Descripciondad")
        }
        if (recordatorio.isBlank()) {
            emptyFields.add("recordatorio")
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


        viewModelScope.launch {
            try {
                if (evento.idevento > 0 ){
                    eventoRepository.update(evento)
                }
                else{
                    eventoRepository.upsert(evento)
                }
                _state.update {
                    it.copy(
                        successMessage = "Se guardó correctamente",
                        error = null,
                        isLoading = false,
                        evento = EventoEntity(),
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

}
data class EventoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val evento: EventoEntity = EventoEntity(),
    val successMessage: String? = null,
    val emptyFields: List<String> = listOf(),

)

sealed interface EventoEvent {
    data class idevento(val idevento: String) : EventoEvent
    data class nombreEvento(val nombreEvento: String) : EventoEvent
    data class fecha(val fecha: String) : EventoEvent
    data class descripcion(val descripcion: String) : EventoEvent
    data class recordatorio(val recordatorio: String) : EventoEvent

    data class onDelete(val evento: EventoEntity ) : EventoEvent
    data class onSearch(val id: Int ) : EventoEvent

    object onSave : EventoEvent
    object onNew : EventoEvent

}
