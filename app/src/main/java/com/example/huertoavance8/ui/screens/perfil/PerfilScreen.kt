package com.example.huertoavance8.ui.screens.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertoavance8.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { UsuarioRepository(context) }
    val scope = rememberCoroutineScope()

    // 🔹 Estado para mostrar los datos del usuario
    var usuarioNombre by remember { mutableStateOf("") }
    var usuarioCorreo by remember { mutableStateOf("") }

    // Cargar usuario desde la BD (el primero encontrado)
    LaunchedEffect(Unit) {
        scope.launch {
            val usuarios = repository.getAll()
            if (usuarios.isNotEmpty()) {
                usuarioNombre = usuarios.last().nombre   // último usuario registrado
                usuarioCorreo = usuarios.last().correo
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("👤 Perfil de usuario") },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Inicio", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🌿 Bienvenido a tu perfil", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Nombre: $usuarioNombre", fontWeight = FontWeight.Bold)
                    Text("Correo: $usuarioCorreo")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Botón cerrar sesión
            Button(
                onClick = {
                    // “Cerrar sesión” → vuelve al Login
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión 🔒")
            }
        }
    }
}
