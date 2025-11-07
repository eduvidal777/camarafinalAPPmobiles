package com.example.huertoavance8.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertoavance8.data.model.Producto
import com.example.huertoavance8.data.model.Carrito
import com.example.huertoavance8.data.repository.ProductoRepository
import com.example.huertoavance8.data.repository.CarritoRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(navController: NavController) {
    val context = LocalContext.current
    val productoRepo = remember { ProductoRepository(context) }
    val carritoRepo = remember { CarritoRepository(context) }
    val scope = rememberCoroutineScope()

    var listaProductos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // 🔹 Cargar productos iniciales si la tabla está vacía
    LaunchedEffect(Unit) {
        scope.launch {
            val existentes = productoRepo.getAllProductos()
            if (existentes.isEmpty()) {
                val productosIniciales = listOf(
                    Producto(
                        id = 1,
                        nombre = "Plantín de Tomate",
                        descripcion = "Planta joven lista para trasplantar.",
                        precio = 2000.0,
                        categoria = "Hortalizas"
                    ),
                    Producto(
                        id = 2,
                        nombre = "Semillas de Lechuga",
                        descripcion = "Semillas frescas y orgánicas listas para sembrar.",
                        precio = 1500.0,
                        categoria = "Semillas"
                    ),
                    Producto(
                        id = 3,
                        nombre = "Fertilizante Natural",
                        descripcion = "Ideal para nutrir tus plantas de forma ecológica.",
                        precio = 3500.0,
                        categoria = "Fertilizantes"
                    )
                )
                productosIniciales.forEach { productoRepo.insertProducto(it) }
            }
            listaProductos = productoRepo.getAllProductos()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("🌿 Productos disponibles") },
                actions = {
                    TextButton(onClick = { navController.navigate("carrito") }) {
                        Text("Ver carrito 🛒", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (listaProductos.isEmpty()) {
                Text(
                    text = "No hay productos disponibles 🌱",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(listaProductos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = producto.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = producto.descripcion)
                                Text("💰 ${producto.precio} CLP")
                                Text("🌱 Categoría: ${producto.categoria}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        scope.launch {
                                            val nuevoItem = Carrito(
                                                productoId = producto.id,
                                                nombreProducto = producto.nombre,
                                                precioUnitario = producto.precio,
                                                cantidad = 1
                                            )
                                            carritoRepo.insert(nuevoItem)

                                            // 🔔 Mostrar mensaje visual
                                            snackbarHostState.showSnackbar(
                                                message = "${producto.nombre} agregado al carrito 🛒",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Agregar al carrito 🛒")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
