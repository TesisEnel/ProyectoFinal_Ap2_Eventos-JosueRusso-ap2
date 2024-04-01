package com.ucne.instantticket.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ucne.instantticket.data.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Upsert
    suspend fun upsert(evento: EventoEntity)

    @Delete
    suspend fun delete(evento: EventoEntity)

    @Query("Select * From evento")
    fun getAllEvent(): Flow<List<EventoEntity>>

    @Update
    suspend fun update(evento: EventoEntity)

    @Query("Select * From evento Where idevento = :id")
    fun getEventoId(id: Int): Flow<EventoEntity>
}