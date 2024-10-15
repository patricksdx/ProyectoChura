package com.sandur.proyectochura.view.labelCard.pet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.proyectochura.model.Mascota
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.components.DrawMascota
import com.sandur.proyectochura.view.labelCard.favorite.FavoriteActivity
import com.sandur.proyectochura.view.labelCard.shop.ShopActivity
import com.sandur.proyectochura.view.labelCard.user.UserActivity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.PawSolid
import compose.icons.lineawesomeicons.Star
import compose.icons.lineawesomeicons.StoreSolid
import compose.icons.lineawesomeicons.UserSolid
import org.json.JSONArray

class MascotasActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoChuraTheme {
                val mascotas = remember { mutableStateOf<List<Mascota>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    loadMascotas(mascotas, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Mascotas") },
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(all = 10.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable { /* Acción para Home */ },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.HomeSolid,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = "Home",
                                        tint = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                    Text(
                                        text = "Home",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@MascotasActivity,
                                                    MascotasActivity::class.java
                                                )
                                            )
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.PawSolid,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = "Mascotas",
                                        tint = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                    Text(
                                        text = "Pets",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@MascotasActivity,
                                                    ShopActivity::class.java
                                                )
                                            )
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.StoreSolid,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = "Shop",
                                        tint = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                    Text(
                                        text = "Shop",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Favorite
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@MascotasActivity,
                                                    FavoriteActivity::class.java
                                                )
                                            )
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.Star,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = "Favorite",
                                        tint = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                    Text(
                                        text = "Favorite",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Profile
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(
                                                Intent(
                                                    this@MascotasActivity,
                                                    UserActivity::class.java
                                                )
                                            )
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.UserSolid,
                                        modifier = Modifier.size(20.dp),
                                        contentDescription = "Profile",
                                        tint = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                    Text(
                                        text = "Profile",
                                        fontSize = 10   .sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when {
                            isLoading.value -> {
                                Text(
                                    text = "Cargando Mascotas...",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                            errorMessage.value != null -> {
                                Text(
                                    text = "Error: ${errorMessage.value}",
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            else -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2), // Cambié a 2 columnas para una mejor visualización
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
            }
        }
    }

    private fun loadMascotas(
        mascotasState: MutableState<List<Mascota>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api-proyectochura-express.onrender.com/mascotas" // Asegúrate que esta URL sea correcta

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
        queue.add(stringRequest)
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
            val ubicacion = jsonObject.optString("ubicacion","")

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
}