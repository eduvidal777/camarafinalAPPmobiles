package com.example.huertoavance8.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertoavance8.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { UsuarioRepository(context) }
    val scope = rememberCoroutineScope()

    // Campos del formulario
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Estado para mostrar carga o mensajes
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("🔐 Iniciar sesión") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo correo
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo contraseña
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de login
                Button(
                    onClick = {
                        if (correo.isBlank() || contrasena.isBlank()) {
                            Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        scope.launch {
                            try {
                                isLoading = true
                                val usuario = repository.login(correo, contrasena)

                                if (usuario != null) {
                                    Toast.makeText(context, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home")
                                } else {
                                    Toast.makeText(context, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(context, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_LONG).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Cargando..." else "Entrar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Enlace al registro
                TextButton(onClick = { navController.navigate("register") }) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}
