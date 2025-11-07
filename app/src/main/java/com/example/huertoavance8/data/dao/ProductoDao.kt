package com.example.huertoavance8.data.dao

import androidx.room.*
import com.example.huertoavance8.data.model.Producto

@Dao
interface ProductoDao {

    @Query("SELECT * FROM producto")
    suspend fun getAll(): List<Producto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

}

