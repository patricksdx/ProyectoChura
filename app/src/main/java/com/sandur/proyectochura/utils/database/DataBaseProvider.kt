package com.sandur.proyectochura.utils.database

import android.content.Context
import androidx.room.Room
import com.sandur.proyectochura.database.UserDataBase

object DataBaseProvider {
    @Volatile
    private var INSTANCE: UserDataBase? = null

    fun getDatabase(context: Context): UserDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDataBase::class.java,
                "user_database"
            ).fallbackToDestructiveMigration() // Opcional: elimina los datos si hay un cambio en la versión de la base de datos
                .build()
            INSTANCE = instance
            instance
        }
    }
}
