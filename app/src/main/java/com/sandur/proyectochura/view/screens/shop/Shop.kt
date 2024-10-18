package com.sandur.proyectochura.view.screens.shop

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
import com.sandur.proyectochura.model.Categorias
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.components.DrawCategorias
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shop(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Tienda") })
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
    val categorias = remember { mutableStateOf<List<Categorias>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loadCategorias(context, categorias, isLoading, errorMessage)
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
                Text(text = "Cargando Categorias...")
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
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(categorias.value) { categoria ->
                        DrawCategorias(categoria, navController)
                    }
                }
            }
        }
    }
}

private fun loadCategorias(
    context: Context,
    categoriasState: MutableState<List<Categorias>>,
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String?>
) {
    val queue = Volley.newRequestQueue(context) // Crear la cola de solicitudes
    val url = "https://api-proyectochura-express.onrender.com/categorias" // Asegúrate de que esta URL sea válida

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            Log.d("Volley", response)
            val categorias = processCategoriasResponse(response)
            categoriasState.value = categorias
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

private fun processCategoriasResponse(response: String): List<Categorias> {
    val jsonArray = JSONArray(response)
    val categoriasList = mutableListOf<Categorias>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val idcategoria = jsonObject.optInt("idcategoria", 0)
        val nombrecategoria = jsonObject.optString("nombrecategoria", "N/A")
        val imagencategoria = jsonObject.optString("imagencategoria", "N/A")
        val descripcion = jsonObject.optString("descripcion", "N/A")
        val total = jsonObject.optInt("total", 0)

        val categoria = Categorias(
            idcategoria = idcategoria,
            nombrecategoria = nombrecategoria,
            imagencategoria = imagencategoria,
            descripcion = descripcion,
            total = total
        )
        categoriasList.add(categoria)
    }
    return categoriasList
}
