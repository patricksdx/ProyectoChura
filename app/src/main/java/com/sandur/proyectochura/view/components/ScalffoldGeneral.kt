import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sandur.proyectochura.navegation.AppScreens
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.PawSolid
import compose.icons.lineawesomeicons.Star
import compose.icons.lineawesomeicons.StoreSolid
import compose.icons.lineawesomeicons.UserSolid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScallfoldGeneral(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    // Obtenemos la ruta actual dentro de un bloque @Composable
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Adopt Me...") },
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
                            .clickable { navigateIfNotCurrent(navController, AppScreens.MainScreen.route, currentRoute) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.HomeSolid,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Home",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .clickable { navigateIfNotCurrent(navController, AppScreens.PetCategory.route, currentRoute) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.PawSolid,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Mascotas",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Pets",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .clickable { /* Acción para Shop */ },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.StoreSolid,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Shop",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Shop",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .clickable { /* Acción para Favoritos */ },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.Star,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Favorite",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .clickable { /* Acción para Perfil */ },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = LineAwesomeIcons.UserSolid,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Profile",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

// Función no @Composable
fun navigateIfNotCurrent(navController: NavHostController, route: String, currentRoute: String?) {
    if (currentRoute != route) {
        navController.navigate(route)
    }
}
