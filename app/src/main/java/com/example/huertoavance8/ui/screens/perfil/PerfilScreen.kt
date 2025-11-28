package com.example.huertoavance8.ui.screens.perfil

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
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

    // 🔹 Foto de perfil SOLO en memoria (no se guarda en Room)
    var fotoUri by rememberSaveable { mutableStateOf<String?>(null) }

    // Launcher para abrir la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoUri = uri?.toString()   // guardamos el string del Uri en memoria
    }

    // Cargar usuario desde la BD (el primero encontrado)
    LaunchedEffect(Unit) {
        scope.launch {
            val usuarios = repository.getAll()
            if (usuarios.isNotEmpty()) {
                val ultimo = usuarios.last()       // último usuario registrado
                usuarioNombre = ultimo.nombre
                usuarioCorreo = ultimo.correo
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
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 AVATAR / FOTO DE PERFIL
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri != null) {
                    AsyncImage(
                        model = fotoUri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    // Inicial del usuario o una U por defecto
                    Text(
                        text = (usuarioNombre.firstOrNull() ?: 'U').uppercaseChar().toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Cambiar foto de perfil")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("🌿 Bienvenido a tu perfil", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

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
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión 🔒")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("scanner") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📷 Escanear código QR")
            }
        }
    }
}
