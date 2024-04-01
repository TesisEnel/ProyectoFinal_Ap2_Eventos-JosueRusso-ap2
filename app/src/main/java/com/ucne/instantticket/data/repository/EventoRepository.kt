package com.ucne.instantticket.data.repository

import com.ucne.instantticket.data.dao.EventoDao
import com.ucne.instantticket.data.dao.UsuarioDao
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.data.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventoRepository@Inject constructor(
    private  val eventoDao: EventoDao
) {
    suspend fun  upsert(eventoEntity: EventoEntity){
        eventoDao.upsert(eventoEntity)
    }

    suspend fun  delete(eventoEntity: EventoEntity){
        eventoDao.delete(eventoEntity)
    }

    suspend fun  update(eventoEntity: EventoEntity){
        eventoDao.update(eventoEntity)
    }

    fun getEventoId(id: Int): Flow<EventoEntity> {
        return eventoDao.getEventoId(id)
    }

    fun getEvento(): Flow<List<EventoEntity>> {
        return eventoDao.getAllEvent()
    }
}