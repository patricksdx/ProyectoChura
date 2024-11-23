package com.sandur.proyectochura.view.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sandur.proyectochura.dao.UserDao
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.utils.database.DataBaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val userDao: UserDao = DataBaseProvider.getDatabase(context).userDao()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Perfil",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyContent(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    isLoggingIn = isLoggingIn,
                    onLoginClick = {
                        isLoggingIn = true
                        scope.launch {
                            val user = withContext(Dispatchers.IO) {
                                userDao.getUserByEmailAndPassword(email, password)
                            }
                            if (user != null) {
                                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                navController.navigate("UserScreen") // Cambia "home" según tu navegación.
                            } else {
                                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                            isLoggingIn = false
                        }
                    },
                    navController = navController // Se pasa el navController a BodyContent
                )
            }
        }
    }
}

@Composable
private fun BodyContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isLoggingIn: Boolean,
    onLoginClick: () -> Unit,
    navController: NavHostController // Añadido el controlador de navegación aquí
) {
    Text(
        text = "Inicio de Sesión",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.padding(8.dp)
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.padding(8.dp)
    )

    Button(
        onClick = onLoginClick,
        enabled = !isLoggingIn,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = if (isLoggingIn) "Iniciando..." else "Iniciar Sesión")
    }

    // Botón para redirigir a la pantalla de agregar usuario
    Button(
        onClick = { navController.navigate("agregaruser") }, // Cambia "agregaruser" por la ruta correcta
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Agregar Usuario")
    }
}