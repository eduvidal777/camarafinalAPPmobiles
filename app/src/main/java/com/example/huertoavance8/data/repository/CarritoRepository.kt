package com.example.huertoavance8.data.repository

import android.content.Context
import androidx.room.Room
import com.example.huertoavance8.data.database.AppDatabase

import com.example.huertoavance8.data.model.Carrito

class CarritoRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "huerto_db"
    )
        .fallbackToDestructiveMigration() // 💣 fuerza la recreación si hay conflictos
        .build()


    private val carritoDao = db.carritoDao()

    // Obtener todos los productos del carrito
    suspend fun getAll(): List<Carrito> = carritoDao.getAll()

    // Agregar producto al carrito
    suspend fun insert(carrito: Carrito) = carritoDao.insert(carrito)

    // Actualizar cantidad de producto
    suspend fun update(carrito: Carrito) = carritoDao.update(carrito)

    // Eliminar producto del carrito
    suspend fun delete(carrito: Carrito) = carritoDao.delete(carrito)

    suspend fun clearAll() {
        carritoDao.clearAll()
    }

}
