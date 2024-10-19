package com.sandur.proyectochura.view.screens.shop

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.sandur.proyectochura.model.Producto
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.components.DrawProducto
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(navController: NavHostController, idcategoria: String?, nombrecategoria: String?) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text (
                            text = "$nombrecategoria",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) },
                    navigationIcon = {
                        IconButton(onClick = {navController.navigateUp()}) {
                            Icon(
                                imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                modifier = Modifier
                                    .size(24.dp),
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
                    .padding(horizontal = 15.dp)
            ) {
                BodyContent(navController = navController, idcategoria = idcategoria)
            }
        }
    }
}

@Composable
fun BodyContent(navController: NavHostController, idcategoria: String?) {
    val context = LocalContext.current // Obtenemos el contexto
    val productos = remember { mutableStateOf<List<Producto>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(idcategoria) {
        loadProductos(context, idcategoria, productos, isLoading, errorMessage)
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
                Text(text = "Cargando Productos...")
            }
            errorMessage.value != null -> {
                Text(
                    text = "Error: ${errorMessage.value}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp),
                ) {
                    items(productos.value) { producto ->
                        DrawProducto(producto, navController)
                    }
                }
            }
        }
    }
}

private fun loadProductos(
    context: Context,
    idcategoria: String?,
    productosState: MutableState<List<Producto>>,
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String?>
) {
    val queue = Volley.newRequestQueue(context)
    val url = "https://api-proyectochura-express.onrender.com/tienda?idcategoria=$idcategoria"

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            Log.d("Volley", response)
            val productos = processProductosResponse(response)
            productosState.value = productos
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

private fun processProductosResponse(response: String): List<Producto> {
    val jsonArray = JSONArray(response)
    val productosList = mutableListOf<Producto>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val idproducto = jsonObject.optInt("idproducto", 0)
        val idcategoria = jsonObject.optInt("idcategoria", 0)
        val nombreproducto = jsonObject.optString("nombreproducto", "N/A")
        val cantidad = jsonObject.optInt("cantidad", 0)
        val detalles = jsonObject.optString("detalles", "N/A")
        val precio = jsonObject.optString("precio", "N/A")
        val preciodescuento = jsonObject.optString("preciodescuento", null.toString())
        val imagenproducto = jsonObject.optString("imagenproducto", "N/A")

        val producto = Producto(
            idproducto = idproducto,
            idcategoria = idcategoria,
            nombreproducto = nombreproducto,
            cantidad = cantidad,
            detalles = detalles,
            precio = precio,
            preciodescuento = preciodescuento,
            imagenproducto = imagenproducto
        )
        productosList.add(producto)
    }
    return productosList
}
