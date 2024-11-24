import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sandur.proyectochura.utils.navegation.AppScreens
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.GlobeSolid
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.PawSolid
import compose.icons.lineawesomeicons.StoreSolid
import compose.icons.lineawesomeicons.UserSolid

@Composable
fun ScallfoldGeneral(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp, horizontal = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Home
                    BottomNavItem(
                        icon = LineAwesomeIcons.HomeSolid,
                        label = "Home",
                        isSelected = currentRoute == AppScreens.MainScreen.route
                    ) {
                        navigateToScreen(navController, AppScreens.MainScreen.route, currentRoute)
                    }

                    // Pets
                    BottomNavItem(
                        icon = LineAwesomeIcons.PawSolid,
                        label = "Pets",
                        isSelected = currentRoute == AppScreens.PetCategory.route
                    ) {
                        navigateToScreen(navController, AppScreens.PetCategory.route, currentRoute)
                    }

                    // Shop
                    BottomNavItem(
                        icon = LineAwesomeIcons.StoreSolid,
                        label = "Shop",
                        isSelected = currentRoute == AppScreens.Shop.route
                    ) {
                        navigateToScreen(navController, AppScreens.Shop.route, currentRoute)
                    }

                    // Favorite
                    BottomNavItem(
                        icon = LineAwesomeIcons.GlobeSolid,
                        label = "Paises",
                        isSelected = currentRoute == AppScreens.Paises.route
                    ) {
                        navigateToScreen(navController, AppScreens.Paises.route, currentRoute)
                    }

                    // Profile
                    BottomNavItem(
                        icon = LineAwesomeIcons.UserSolid,
                        label = "User",
                        isSelected = currentRoute == AppScreens.Login.route
                    ) {
                        navigateToScreen(navController, AppScreens.Login.route, currentRoute)
                    }
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            modifier = if (isSelected) {
                Modifier
                    .size(22.dp)
                    .graphicsLayer(scaleX = 1.2f, scaleY = 1.2f)
            } else {
                Modifier.size(20.dp)
            },
            contentDescription = label,
            tint = if (isSelected) { MaterialTheme.colorScheme.secondary  } else { MaterialTheme.colorScheme.primary }
        )
        Text(
            text = label,
            fontSize = if(isSelected) { 14.sp } else { 12.sp },
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
        )
    }
}

fun navigateToScreen(navController: NavHostController, route: String, currentRoute: String?) {
    if (currentRoute != route) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}