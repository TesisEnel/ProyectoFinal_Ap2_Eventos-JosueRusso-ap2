package com.ucne.instantticket.data.repository

import com.ucne.instantticket.data.dao.UsuarioDao
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

    fun getUsuario(): Flow<List<UsuarioEntity>> {
        return usuarioDao.getAll()
    }
}