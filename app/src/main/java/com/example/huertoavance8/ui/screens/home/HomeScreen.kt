package com.example.huertoavance8.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertoavance8.viewmodel.UsuarioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: UsuarioViewModel = viewModel()
    val usuarioActual = viewModel.usuarioActual.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏡 Huerto Hogar") },
                actions = {
                    TextButton(onClick = { navController.navigate("perfil") }) {
                        Text("Perfil", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val nombre = usuarioActual.value?.nombre ?: "usuario"
                Text(
                    text = "🌱 Bienvenido, $nombre 🌿",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { navController.navigate("productos") }) {
                    Text("Ver Productos")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { navController.navigate("carrito") }) {
                    Text("Ir al Carrito")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { navController.navigate("perfil") }) {
                    Text("Mi Perfil")
                }
            }
        }
    }
}
