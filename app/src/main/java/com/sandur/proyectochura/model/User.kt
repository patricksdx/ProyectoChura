package com.sandur.proyectochura.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val name: String,
    val lastName: String,
    val createdAt: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
)
