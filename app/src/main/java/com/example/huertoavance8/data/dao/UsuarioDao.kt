package com.example.huertoavance8.data.dao

import androidx.room.*
import com.example.huertoavance8.data.model.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun login(correo: String, contrasena: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun getAll(): List<Usuario>
}
