package com.sandur.proyectochura.view.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sandur.proyectochura.dao.UserDao
import com.sandur.proyectochura.model.User
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import com.sandur.proyectochura.utils.database.DataBaseProvider
import com.sandur.proyectochura.utils.userstore.UserStore
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.WindowCloseSolid
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun User(navController: NavHostController) {
    val context = LocalContext.current
    val userDao: UserDao = DataBaseProvider.getDatabase(context).userDao()
    val userStore = UserStore(context)

    var currentUser by remember { mutableStateOf<User?>(null) }
    val userList by userDao.getAllUsers().collectAsState(initial = emptyList())
    var selectedUsers by remember { mutableStateOf(setOf<User>()) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var showAlertDialog by remember { mutableStateOf(false) }

    // Obtenemos el ID del usuario actual desde UserStore
    LaunchedEffect(Unit) {
        scope.launch {
            userStore.currentUserId.collect { userId ->
                if (userId != null) {
                    currentUser = userDao.getUserById(userId)
                }
            }
        }
    }

    ProyectoChuraTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "") }) },
            bottomBar = { BottomAppBar {} }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentUser?.let { user ->
                    Text(
                        text = "Bienvenido, ${user.name} ${user.lastName}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } ?: Text(
                    text = "Cargando usuario...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                UserTable(
                    userList = userList,
                    selectedUsers = selectedUsers,
                    onUserSelected = { user, isSelected ->
                        selectedUsers = if (isSelected) {
                            selectedUsers + user
                        } else {
                            selectedUsers - user
                        }
                    }
                )

                // Botones para acciones
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("AgregarUser") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Agregar")
                    }
                    Button(
                        onClick = {
                            if (selectedUsers.size == 1) {
                                val userId = selectedUsers.first().id
                                navController.navigate("modify_user/$userId")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = selectedUsers.size == 1
                    ) {
                        Text(text = "Modificar")
                    }
                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.weight(1f),
                        enabled = selectedUsers.isNotEmpty()
                    ) {
                        Text(text = "Eliminar")
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                ) {
                    Button(
                        onClick = {
                            showAlertDialog = true
                        }, // Activar el estado para mostrar el diálogo
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Cerrar sesión")
                    }
                }

                if (showAlertDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialog = false
                        }, // Cerrar el diálogo al hacer clic fuera
                        icon = {
                            Icon(
                                imageVector = LineAwesomeIcons.WindowCloseSolid,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Cerrar"
                            )
                        },
                        title = {
                            Text(
                                text = "Cerrar Sesión",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        text = {
                            Text(
                                text = "¿Desea cerrar sesión?",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        confirmButton = {
                            ElevatedButton(
                                onClick = {
                                    scope.launch {
                                        // Realizar el cierre de sesión
                                        userStore.clearCurrentUser()
                                        navController.navigate("login")
                                    }
                                    showAlertDialog = false // Cerrar el diálogo
                                }
                            ) {
                                Text(text = "Sí")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showAlertDialog = false
                                } // Cerrar el diálogo si se cancela
                            ) {
                                Text(text = "No")
                            }
                        }
                    )
                }
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text(text = "Eliminar usuarios") },
                    text = { Text(text = "¿Estás seguro de que deseas eliminar los usuarios seleccionados?") },
                    confirmButton = {
                        Button(onClick = {
                            scope.launch {
                                selectedUsers.forEach { user ->
                                    userDao.deleteUser(user)
                                }
                                selectedUsers = emptySet()
                            }
                            showDeleteDialog = false
                        }) {
                            Text(text = "Sí")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDeleteDialog = false }) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }
        }
    }
}

// Tabla de usuarios con selección
@Composable
fun UserTable(
    userList: List<User>,
    selectedUsers: Set<User>,
    onUserSelected: (User, Boolean) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(userList) { user ->
            UserRow(
                user = user,
                isSelected = user in selectedUsers,
                onSelectionChanged = { isSelected -> onUserSelected(user, isSelected) }
            )
        }
    }
}

@Composable
fun UserRow(
    user: User,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Mostrar el nombre del usuario
        Text(
            text = user.name,
            modifier = Modifier.weight(1f)
        )
        // Mostrar el correo del usuario
        Text(
            text = user.email,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
        // Mostrar la fecha de creación del usuario
        Text(
            text = user.createdAt, // Mostrar la fecha de creación
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
        // Checkbox para seleccionar el usuario
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectionChanged
        )
    }
}
