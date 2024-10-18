package com.sandur.proyectochura.view.screens.insert

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
fun Insert(navController: NavHostController) {
    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "") })
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
    Text(text = "Insert")
}