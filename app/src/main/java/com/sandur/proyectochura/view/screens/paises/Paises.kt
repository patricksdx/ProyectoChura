package com.sandur.proyectochura.view.screens.paises

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.sandur.proyectochura.model.Pais
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.components.DrawMascota
import com.sandur.proyectochura.view.components.DrawPais
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscar(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text (
                            text = "Paises",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                )
            },
            bottomBar = { BottomAppBar {} },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("AgregarPais")
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar País")
                }
            }
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
    val context = LocalContext.current
    val paises = remember { mutableStateOf<List<Pais>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loadPaises(context, paises, isLoading, errorMessage)
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
                Text(text = "Cargando Paises...")
            }
            errorMessage.value != null -> {
                Text(
                    text = "Error: ${errorMessage.value}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp),
                ) {
                    items(paises.value) { pais ->
                        DrawPais(pais)
                    }
                }
            }
        }
    }
}


private fun loadPaises(
    context: Context,
    paisesState: MutableState<List<Pais>>,
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String?>
) {
    val queue = Volley.newRequestQueue(context)
    val url = "https://servicios.campus.pe/paises.php"

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            Log.d("Volley", response)
            val paises = processPaisesResponse(response)
            paisesState.value = paises
            isLoading.value = false
        },
        { error ->
            Log.e("Volley", "Error en la solicitud: ${error.message}")
            errorMessage.value = "Error en la solicitud. Intente nuevamente."
            isLoading.value = false
        }
    )
    queue.add(stringRequest)
}

private fun processPaisesResponse(response: String): List<Pais> {
    val jsonArray = JSONArray(response)
    val paises = mutableListOf<Pais>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val idpais = jsonObject.optString("idpais", "")
        val codpais = jsonObject.optString("codpais", "N/A")
        val pais = jsonObject.optString("pais", "N/A")
        val capital = jsonObject.optString("capital", "N/A")
        val area = jsonObject.optString("area", "N/A")
        val poblacion = jsonObject.optString("poblacion", "N/A")
        val continente = jsonObject.optString("continente", "N/A")

        val paisData = Pais(
            idpais = idpais,
            codpais = codpais,
            pais = pais,
            capital = capital,
            area = area,
            poblacion = poblacion,
            continente = continente
        )
        paises.add(paisData)
    }
    return paises
}
