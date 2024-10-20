package com.sandur.proyectochura.view.screens.profile

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Perfil",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                )
            },
            bottomBar = { BottomAppBar {} }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                BodyContent(navController = navController)
            }
        }
    }
}

@Composable
private fun BodyContent(navController: NavHostController) {
    val context = LocalContext.current // Obtener el contexto
    val webView = remember { WebView(context) } // Crear una WebView

    // Estado para controlar si se muestra el diálogo de confirmación
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var savedPdfUri by remember { mutableStateOf<Uri?>(null) }

    // Recordar el lanzador para el selector de archivos
    val createPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        uri?.let {
            // Llamar a la función de generar PDF cuando se seleccione la ubicación
            savedPdfUri = it
            showConfirmationDialog = true // Mostrar el diálogo después de guardar el PDF
        }
    }

    // Función para abrir el PDF
    fun abrirPdf(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile")
        Button(
            onClick = {
                // Datos que quieres pasar al HTML
                val nombre = "Patrick"
                val info = "información importante sobre el documento."

                // Cargar HTML y exportar a PDF
                // loadHtmlAndExportAsPdf(webView, context, nombre, info)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Generar PDF")
        }

        // Mostrar diálogo de confirmación después de guardar el PDF
        if (showConfirmationDialog && savedPdfUri != null) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("PDF Guardado") },
                text = { Text("El PDF ha sido guardado. ¿Deseas abrirlo?") },
                confirmButton = {
                    Button(
                        onClick = {
                            savedPdfUri?.let { abrirPdf(it) }
                            showConfirmationDialog = false
                        }
                    ) {
                        Text("Abrir")
                    }
                },
                dismissButton = {
                    Button(onClick = { showConfirmationDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}