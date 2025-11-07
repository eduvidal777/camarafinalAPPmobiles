package com.example.huertoavance8.data.dao

import androidx.room.*
import com.example.huertoavance8.data.model.Carrito

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito")
    suspend fun getAll(): List<Carrito>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carrito: Carrito)

    @Update
    suspend fun update(carrito: Carrito)

    @Delete
    suspend fun delete(carrito: Carrito)

    @Query("DELETE FROM carrito")
    suspend fun clearAll()
    }
