package com.ucne.instantticket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int = 0,
    val nombreCompleto: String = "",
    val nombreUsuario: String = "",
    val edad:  Int = 0,
    val fechaNacimiento: String = "",
    val email: String = "",
    val password: String = "",

)