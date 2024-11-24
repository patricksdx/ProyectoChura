package com.sandur.proyectochura.utils.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sandur.proyectochura.database.UserDataBase
import com.sandur.proyectochura.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DataBaseProvider {
    @Volatile
    private var INSTANCE: UserDataBase? = null

    fun getDatabase(context: Context): UserDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDataBase::class.java,
                "user_database"
            )
                .fallbackToDestructiveMigration() // Opcional: elimina los datos si hay un cambio en la versión
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Inserción del usuario administrador
                        CoroutineScope(Dispatchers.IO).launch {
                            val userDao = getDatabase(context).userDao()
                            // Se asigna un apellido y se usa la fecha predeterminada de createdAt
                            userDao.insertUser(
                                User(
                                    email = "1234",
                                    password = "1234",
                                    name = "Admin",
                                    lastName = "Admin", // Apellido por defecto
                                    createdAt = SimpleDateFormat(
                                        "dd/MM/yyyy",
                                        Locale.getDefault()
                                    ).format(
                                        Date()
                                    ) // Fecha de creación
                                )
                            )
                        }
                    }
                })
                .build()
            INSTANCE = instance
            instance
        }
    }
}
