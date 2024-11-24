package com.sandur.proyectochura.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sandur.proyectochura.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Insertar un usuario
    @Insert
    suspend fun insertUser(user: User)

    // Eliminar un usuario específico
    @Delete
    suspend fun deleteUser(user: User)

    // Eliminar un usuario por correo electrónico
    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    // Obtener todos los usuarios en la tabla
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    // Validar las credenciales del usuario (inicio de sesión)
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    // Obtener un usuario por su ID
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): User?

    // Verificar si un correo electrónico ya está registrado
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailRegistered(email: String): Int

    // Eliminar todos los usuarios de la tabla (solo si necesario)
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    // Modificar un usuario sin modificar la fecha de creación
    @Query(
        """
        UPDATE users 
        SET name = :name, email = :email, password = :password, lastName = :apellidos
        WHERE id = :id
    """
    )
    suspend fun updateUserWithoutId(
        id: Int,
        name: String,
        email: String,
        password: String,
        apellidos: String
    )
}