package com.sandur.proyectochura.view.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.utils.database.DataBaseProvider
import com.sandur.proyectochura.utils.userstore.UserStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current
    val userDao: UserDao = DataBaseProvider.getDatabase(context).userDao()
    val userStore = UserStore(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) } // Estado para el checkbox
    val scope = rememberCoroutineScope()

    // Verificar si el usuario seleccionó mantener sesión iniciada
    LaunchedEffect(Unit) {
        scope.launch {
            userStore.isSessionPersistent.collect { isPersistent ->
                if (isPersistent) {
                    // Si el usuario desea mantener la sesión, navegar automáticamente
                    userStore.currentUserId.collect { userId ->
                        if (userId != null) {
                            navController.navigate("User") {
                                popUpTo("Login") { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }

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
                            val user = userDao.getUserByEmailAndPassword(email, password)
                            if (user != null) {
                                // Guardar siempre el ID del usuario que inició sesión
                                userStore.saveCurrentUser(user.id)

                                // Si el checkbox está marcado, guardar la persistencia de sesión
                                userStore.setSessionPersistence(rememberMe)

                                Toast.makeText(
                                    context,
                                    "Inicio de sesión exitoso",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("User")
                            } else {
                                Toast.makeText(
                                    context,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            isLoggingIn = false
                        }
                    },
                    navController = navController,
                    rememberMe = rememberMe,
                    onRememberMeChange = { rememberMe = it }
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
    navController: NavHostController,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit
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

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = rememberMe,
            onCheckedChange = onRememberMeChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Recordar inicio de sesion")
    }

    Button(
        onClick = onLoginClick,
        enabled = !isLoggingIn,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = if (isLoggingIn) "Iniciando..." else "Iniciar Sesión")
    }
}