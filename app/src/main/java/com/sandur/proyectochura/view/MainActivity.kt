package com.sandur.proyectochura.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sandur.proyectochura.navegation.AppNavegation
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoChuraTheme {
                AppNavegation()
            }
        }
    }
}
