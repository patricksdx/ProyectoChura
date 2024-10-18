package com.sandur.proyectochura.view.screens.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(navController: NavHostController, idcategoria: String?, nombrecategoria: String?) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = nombrecategoria ?: "Categoría desconocida") }) // Muestra el nombre de la categoría
            },
            bottomBar = { BottomAppBar {} }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                BodyContent(navController = navController, idcategoria = idcategoria, nombrecategoria = nombrecategoria)
            }
        }
    }
}

@Composable
fun BodyContent(navController: NavHostController, idcategoria: String?, nombrecategoria: String?) {
    // Muestra el ID y el nombre de la categoría
    Text(text = "ID de Categoría: $idcategoria")
    Text(text = "Nombre de Categoría: ${nombrecategoria ?: "Desconocido"}")

    // Aquí puedes hacer algo con idcategoria, como cargar productos
    // Por ejemplo, podrías listar productos basados en el idcategoria
}
