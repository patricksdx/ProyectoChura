package com.sandur.proyectochura.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sandur.proyectochura.dao.UserDao
import com.sandur.proyectochura.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}