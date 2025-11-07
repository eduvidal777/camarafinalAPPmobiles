package com.example.huertoavance8.ui.screens.carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertoavance8.data.model.Carrito
import com.example.huertoavance8.data.repository.CarritoRepository
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { CarritoRepository(context) }
    val scope = rememberCoroutineScope()

    var listaCarrito by remember { mutableStateOf<List<Carrito>>(emptyList()) }
    var total by remember { mutableStateOf(0.0) }

    val snackbarHostState = remember { SnackbarHostState() }

    // 🔹 Cargar carrito
    LaunchedEffect(Unit) {
        scope.launch {
            listaCarrito = repository.getAll()
            total = listaCarrito.sumOf { it.precioUnitario * it.cantidad }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("🛒 Tu Carrito") },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Seguir comprando 🌱", color = MaterialTheme.colorScheme.onPrimary)
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
            if (listaCarrito.isEmpty()) {
                Text(
                    text = "Tu carrito está vacío 🧺",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(listaCarrito) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿 ${item.nombreProducto}", style = MaterialTheme.typography.titleMedium)
                                Text("Cantidad: ${item.cantidad}")
                                Text("Precio unitario: ${item.precioUnitario} CLP")
                                Text("Subtotal: ${item.cantidad * item.precioUnitario} CLP")

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        scope.launch {
                                            repository.delete(item)
                                            listaCarrito = repository.getAll()
                                            total = listaCarrito.sumOf { it.precioUnitario * it.cantidad }
                                            snackbarHostState.showSnackbar(
                                                "Producto eliminado del carrito ❌",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "💰 Total: $total CLP",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            repository.clearAll()
                            listaCarrito = emptyList()
                            total = 0.0

                            snackbarHostState.showSnackbar(
                                message = "Compra finalizada con éxito 🎉 ¡Gracias por preferir Huerto Hogar!",
                                duration = SnackbarDuration.Long
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar compra 🥕")
                }
            }
        }
    }
}
