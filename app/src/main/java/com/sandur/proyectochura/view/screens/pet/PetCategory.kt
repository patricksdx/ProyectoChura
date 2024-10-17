package com.sandur.proyectochura.view.screens.pet

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.proyectochura.model.Mascota
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.components.DrawMascota
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetCategory(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Categoría de Mascotas") })
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
    val context = LocalContext.current // Obtenemos el contexto
    val mascotas = remember { mutableStateOf<List<Mascota>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Llamada a la API cuando se carga el Composable
    LaunchedEffect(Unit) {
        loadMascotas(context, mascotas, isLoading, errorMessage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            isLoading.value -> {
                Text(text = "Cargando Mascotas...")
            }
            errorMessage.value != null -> {
                Text(
                    text = "Error: ${errorMessage.value}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 columnas para mostrar las mascotas
                    modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(mascotas.value) { mascota ->
                        DrawMascota(mascota)
                    }
                }
            }
        }
    }
}

private fun loadMascotas(
    context: Context, // Se requiere el contexto para Volley
    mascotasState: MutableState<List<Mascota>>,
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String?>
) {
    val queue = Volley.newRequestQueue(context) // Crear la cola de solicitudes
    val url = "https://api-proyectochura-express.onrender.com/mascotas" // Asegúrate de que esta URL sea válida

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            Log.d("Volley", response)
            val mascotas = processMascotasResponse(response)
            mascotasState.value = mascotas
            isLoading.value = false
        },
        { error ->
            Log.e("Volley", "Error en la solicitud: ${error.message}")
            errorMessage.value = "Error en la solicitud. Intente nuevamente."
            isLoading.value = false
        }
    )
    queue.add(stringRequest) // Añadir la solicitud a la cola
}

private fun processMascotasResponse(response: String): List<Mascota> {
    val jsonArray = JSONArray(response)
    val mascotas = mutableListOf<Mascota>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val idmascota = jsonObject.optString("idmascota", "")
        val especiemascota = jsonObject.optString("especiemascota", "N/A")
        val razamascota = jsonObject.optString("razamascota", "N/A")
        val nombre = jsonObject.optString("nombre", "N/A")
        val foto = jsonObject.optString("foto", "")
        val sexo = jsonObject.optInt("sexo")
        val ubicacion = jsonObject.optString("ubicacion", "")

        val mascota = Mascota(
            idmascota = idmascota,
            especiemascota = especiemascota,
            razamascota = razamascota,
            nombre = nombre,
            foto = foto,
            sexo = sexo,
            ubicacion = ubicacion
        )
        mascotas.add(mascota)
    }
    return mascotas
}