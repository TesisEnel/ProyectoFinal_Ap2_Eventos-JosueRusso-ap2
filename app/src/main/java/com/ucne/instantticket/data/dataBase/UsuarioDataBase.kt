package com.ucne.instantticket.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucne.instantticket.data.dao.EventoDao
import com.ucne.instantticket.data.dao.UsuarioDao
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.data.entity.UsuarioEntity


@Database(entities = [UsuarioEntity::class, EventoEntity::class], version = 1)
abstract class UsuarioDataBase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun EventoDao(): EventoDao
}

