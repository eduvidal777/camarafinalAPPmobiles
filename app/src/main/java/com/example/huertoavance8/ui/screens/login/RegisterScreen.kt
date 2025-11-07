package com.example.huertoavance8.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertoavance8.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {

    val viewModel: UsuarioViewModel = viewModel()
    val registroState = viewModel.registroExitoso.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Mostrar mensaje según resultado del registro
    LaunchedEffect(registroState.value) {
        when (registroState.value) {
            true -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("✅ Usuario registrado correctamente")
                }
                viewModel.resetRegistroState()
                navController.navigate("login")
            }
            false -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("⚠️ Error al registrar usuario")
                }
                viewModel.resetRegistroState()
            }
            null -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "🪴 Crear cuenta", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.registrarUsuario(nombre, correo, contrasena)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = { navController.navigate("login") }) {
                    Text("¿Ya tienes cuenta? Inicia sesión")
                }
            }
        }
    }
}
