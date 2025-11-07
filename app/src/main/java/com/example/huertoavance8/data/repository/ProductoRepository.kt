package com.example.huertoavance8.data.repository

import android.content.Context
import androidx.room.Room
import com.example.huertoavance8.data.database.AppDatabase

import com.example.huertoavance8.data.model.Producto

class ProductoRepository(context: Context) {

    // Conexión con la base de datos Room
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "huerto_db"
    )
        .fallbackToDestructiveMigration() // 💣 fuerza la recreación si hay conflictos
        .build()

    // Acceso al DAO de productos
    private val productoDao = db.productoDao()

    // Obtener todos los productos
    suspend fun getAllProductos(): List<Producto> = productoDao.getAll()

    // Insertar un nuevo producto
    suspend fun insertProducto(producto: Producto) = productoDao.insert(producto)

    // Eliminar un producto existente
    suspend fun deleteProducto(producto: Producto) = productoDao.delete(producto)

    // Actualizar producto existente
    suspend fun updateProducto(producto: Producto) = productoDao.update(producto)

}
