package com.ucne.instantticket.data.repository

import com.ucne.instantticket.data.dao.UsuarioDao
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.data.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {
    suspend fun  upsert(usuarioEntity: UsuarioEntity){
        usuarioDao.upsert(usuarioEntity)
    }

    suspend fun  delete(usuarioEntity: UsuarioEntity){
        usuarioDao.delete(usuarioEntity)
    }

    fun getUsuario(): Flow<UsuarioEntity> {
        return usuarioDao.getUsuario()
    }

    suspend fun  update(usuarioEntity: UsuarioEntity){
        usuarioDao.update(usuarioEntity)
    }

    fun getUsuarioId(id: Int): Flow<UsuarioEntity> {
        return usuarioDao.getUsuarioId(id)
    }
}