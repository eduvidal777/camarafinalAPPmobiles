package com.example.huertoavance8.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoavance8.data.model.Carrito
import com.example.huertoavance8.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class   CarritoViewModel(context: Context) : ViewModel() {

    private val repository = CarritoRepository(context)

    // Estado del carrito (lista de productos)
    private val _carritoItems = MutableStateFlow<List<Carrito>>(emptyList())
    val carritoItems: StateFlow<List<Carrito>> = _carritoItems

    // Total del carrito
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    init {
        // Cargar los productos del carrito al iniciar
        cargarCarrito()
    }

    private fun cargarCarrito() {
        viewModelScope.launch {
            val items = repository.getAll()
            _carritoItems.value = items
            calcularTotal(items)
        }
    }

    private fun calcularTotal(items: List<Carrito>) {
        _total.value = items.sumOf { it.precioUnitario * it.cantidad }
    }

    fun agregarAlCarrito(productoId: Int, nombre: String, precio: Double) {
        viewModelScope.launch {
            // Buscar si el producto ya existe en el carrito
            val items = repository.getAll()
            val existente = items.find { it.productoId == productoId }

            if (existente != null) {
                // Aumentar cantidad
                val actualizado = existente.copy(cantidad = existente.cantidad + 1)
                repository.update(actualizado)
            } else {
                // Agregar nuevo producto
                val nuevo = Carrito(
                    productoId = productoId,
                    nombreProducto = nombre,
                    precioUnitario = precio,
                    cantidad = 1
                )
                repository.insert(nuevo)
            }
            cargarCarrito()
        }
    }

    fun incrementarCantidad(item: Carrito) {
        viewModelScope.launch {
            val actualizado = item.copy(cantidad = item.cantidad + 1)
            repository.update(actualizado)
            cargarCarrito()
        }
    }

    fun disminuirCantidad(item: Carrito) {
        viewModelScope.launch {
            if (item.cantidad > 1) {
                val actualizado = item.copy(cantidad = item.cantidad - 1)
                repository.update(actualizado)
            } else {
                repository.delete(item)
            }
            cargarCarrito()
        }
    }

    fun eliminarItem(item: Carrito) {
        viewModelScope.launch {
            repository.delete(item)
            cargarCarrito()
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.clearAll()
            cargarCarrito()
        }
    }
}
