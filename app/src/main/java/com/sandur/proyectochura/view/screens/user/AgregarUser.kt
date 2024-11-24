package com.sandur.proyectochura.view.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sandur.proyectochura.dao.UserDao
import com.sandur.proyectochura.model.User
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.utils.database.DataBaseProvider
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarUser(navController: NavHostController) {
    val context = LocalContext.current
    val userDao: UserDao = DataBaseProvider.getDatabase(context).userDao()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") } // Variable para apellido
    var isCreatingUser by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ProyectoChuraTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Left"
                            )
                        }
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
                    name = name,
                    onNameChange = { name = it },
                    lastName = lastName,
                    onLastNameChange = { lastName = it }, // Cambio para apellido
                    isCreatingUser = isCreatingUser,
                    onCreateUserClick = {
                        // Validación: Todos los campos deben estar completos
                        if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                            Toast.makeText(
                                context,
                                "Todos los campos son obligatorios",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@BodyContent
                        }

                        isCreatingUser = true
                        scope.launch {
                            // Crear un nuevo usuario en la base de datos
                            val newUser = User(
                                name = name,
                                lastName = lastName,
                                email = email,
                                password = password
                            )
                            withContext(Dispatchers.IO) {
                                userDao.insertUser(newUser)
                            }
                            Toast.makeText(context, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()

                            // Redirigir a la pantalla de usuarios
                            navController.navigate("User") // Asegúrate de que esta ruta sea correcta

                            // Restablecer el estado de la creación
                            isCreatingUser = false
                        }
                    }
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
    name: String,
    onNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit, // Añadir parámetro para apellido
    isCreatingUser: Boolean,
    onCreateUserClick: () -> Unit
) {
    Text(
        text = "Crear Nuevo Usuario",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Nombre") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.padding(8.dp)
    )

    OutlinedTextField(
        value = lastName,
        onValueChange = onLastNameChange, // Actualizar el apellido
        label = { Text("Apellido") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.padding(8.dp)
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
        onClick = onCreateUserClick,
        enabled = !isCreatingUser,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = if (isCreatingUser) "Creando..." else "Crear Usuario")
    }
}
