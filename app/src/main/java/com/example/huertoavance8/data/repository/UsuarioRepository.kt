package com.example.huertoavance8.data.repository

import android.content.Context
import androidx.room.Room
import com.example.huertoavance8.data.database.AppDatabase

import com.example.huertoavance8.data.model.Usuario

class UsuarioRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "huerto_db"
    )
        .fallbackToDestructiveMigration() // 💣 fuerza la recreación si hay conflictos
        .build()

    private val usuarioDao = db.usuarioDao()

    suspend fun login(correo: String, contrasena: String): Usuario? {
        return usuarioDao.login(correo, contrasena)
    }

    suspend fun register(usuario: Usuario) {
        usuarioDao.insert(usuario)
    }

    suspend fun getAll(): List<Usuario> {
        return usuarioDao.getAll()
    }
}
