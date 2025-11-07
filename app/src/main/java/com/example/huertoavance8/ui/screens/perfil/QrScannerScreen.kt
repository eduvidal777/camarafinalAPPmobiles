package com.example.huertoavance8.ui.screens.perfil

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(navController: NavController) {
    val context = LocalContext.current
    var qrResult by remember { mutableStateOf<String?>(null) }

    // Configurar el lanzador del escáner
    val qrLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            qrResult = result.contents
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📷 Escáner QR") },
                actions = {
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Volver", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val options = ScanOptions()
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                options.setPrompt("Escanea un código QR")
                options.setCameraId(0)
                options.setBeepEnabled(true)
                options.setBarcodeImageEnabled(false)
                qrLauncher.launch(options)
            }) {
                Text("Abrir cámara para escanear")
            }

            Spacer(modifier = Modifier.height(24.dp))

            qrResult?.let {
                Text(
                    text = "Resultado: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
