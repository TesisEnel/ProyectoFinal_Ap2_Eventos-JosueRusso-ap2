package com.ucne.instantticket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("evento")
data class EventoEntity(
   @PrimaryKey(autoGenerate = true)
   val idevento: Int = 0,
   val nombreEvento: String = "",
   val fecha: String = "",
   val descripcion: String = "",
   val recordatorio: String = ""
)
