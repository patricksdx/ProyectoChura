package com.sandur.proyectochura.view.screens.paises

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPais(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Agregar País",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Left"
                            )
                        }
                    }
                )
            },
            bottomBar = { BottomAppBar {} }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .wrapContentHeight(Alignment.CenterVertically), // Center vertically
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyContent(navController)
            }
        }
    }
}

@Composable
private fun BodyContent(navController: NavHostController) {
    var codpais by remember { mutableStateOf(TextFieldValue()) }
    var pais by remember { mutableStateOf(TextFieldValue()) }
    var capital by remember { mutableStateOf(TextFieldValue()) }
    var area by remember { mutableStateOf(TextFieldValue()) }
    var poblacion by remember { mutableStateOf(TextFieldValue()) }
    var continente by remember { mutableStateOf(TextFieldValue()) }
    var isSubmitting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Get the context here
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = codpais,
            onValueChange = { codpais = it },
            label = { Text("Código del País") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = pais,
            onValueChange = { pais = it },
            label = { Text("Nombre del País") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = capital,
            onValueChange = { capital = it },
            label = { Text("Capital") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = area,
            onValueChange = { area = it },
            label = { Text("Área") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = poblacion,
            onValueChange = { poblacion = it },
            label = { Text("Población") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = continente,
            onValueChange = { continente = it },
            label = { Text("Continente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Show error message if any
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                // Validar entradas
                if (codpais.text.isEmpty() || pais.text.isEmpty() || capital.text.isEmpty() ||
                    area.text.isEmpty() || poblacion.text.isEmpty() || continente.text.isEmpty()) {
                    errorMessage = "Por favor, completa todos los campos."
                    isSubmitting = false

                } else {
                    errorMessage = "" // Limpiar el mensaje de error
                    isSubmitting = true
                    insertPais(context, navController, codpais.text, pais.text, capital.text, area.text, poblacion.text, continente.text) { success ->
                        isSubmitting = false
                        if (success) {
                            navController.navigate("Paises")
                        }
                    }
                }
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Agregar")
        }
        if (isSubmitting) {
            CircularProgressIndicator()
        }
    }
}

private fun insertPais(context: Context, navController: NavHostController, codpais: String, pais: String, capital: String, area: String, poblacion: String, continente: String, onComplete: (Boolean) -> Unit) {
    Log.d("VOLLEY", "$codpais - $pais - $capital - $area - $poblacion - $continente")
    val queue = Volley.newRequestQueue(context)
    val url = "https://servicios.campus.pe/paisesinsert.php"

    val stringRequest = object : StringRequest(
        Request.Method.POST, url,
        { response ->
            Log.d("VOLLEY", response)
            onComplete(true)
        },
        { error ->
            Log.e("VOLLEY", "Error occurred: ${error.message}", error)
            onComplete(false)
        }
    ) {
        override fun getParams(): MutableMap<String, String> {
            return hashMapOf(
                "codpais" to codpais,
                "pais" to pais,
                "capital" to capital,
                "area" to area,
                "poblacion" to poblacion,
                "continente" to continente,
            )
        }
    }
    queue.add(stringRequest)
}