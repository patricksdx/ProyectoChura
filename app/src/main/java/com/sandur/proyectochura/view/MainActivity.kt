package com.sandur.proyectochura.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.view.labelCard.pet.MascotasActivity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.Star
import compose.icons.lineawesomeicons.StoreSolid
import compose.icons.lineawesomeicons.UserSolid
import compose.icons.lineawesomeicons.PawSolid

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoChuraTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Adoptame") },
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp, horizontal = 20.dp),
                            ) {
                                // Columna para el icono de Home
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
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Mascotas
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(Intent(this@MainActivity, MascotasActivity::class.java))
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
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Shop
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(Intent(this@MainActivity, MascotasActivity::class.java))
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
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Favorite
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(Intent(this@MainActivity, MascotasActivity::class.java))
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
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                                // Columna para el icono de Profile
                                Column(
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            startActivity(Intent(this@MainActivity, MascotasActivity::class.java))
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
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary // Cambiar color al primario
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize() // Asegúrate de que el contenido ocupe el tamaño máximo
                    ) {
                        // Aquí puedes añadir contenido adicional
                    }
                }
            }
        }
    }
}
