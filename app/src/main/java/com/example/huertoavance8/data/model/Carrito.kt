package com.example.huertoavance8.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class Carrito(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID autogenerado por Room

    val productoId: Int,            // ID del producto (referencia)
    val nombreProducto: String,     // Nombre del producto
    val precioUnitario: Double,     // Precio por unidad
    val cantidad: Int = 1           // Cantidad seleccionada por el usuario
)
