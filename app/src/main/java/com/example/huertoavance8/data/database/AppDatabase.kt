package com.example.huertoavance8.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.huertoavance8.data.dao.CarritoDao
import com.example.huertoavance8.data.dao.ProductoDao
import com.example.huertoavance8.data.dao.UsuarioDao
import com.example.huertoavance8.data.model.Carrito
import com.example.huertoavance8.data.model.Producto
import com.example.huertoavance8.data.model.Usuario

@Database(
    entities = [Usuario::class, Producto::class, Carrito::class],
    version = 3, // 🔢 subimos versión para que Room regenere todo
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao
}
