package com.sandur.proyectochura.utils.navegation

import ScallfoldGeneral
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sandur.proyectochura.view.screens.mainScreen.MainScreen
import com.sandur.proyectochura.view.screens.paises.AgregarPais
import com.sandur.proyectochura.view.screens.paises.Buscar
import com.sandur.proyectochura.view.screens.pet.PetCategory
import com.sandur.proyectochura.view.screens.shop.Products
import com.sandur.proyectochura.view.screens.shop.Shop
import com.sandur.proyectochura.view.screens.user.AgregarUser
import com.sandur.proyectochura.view.screens.user.Login
import com.sandur.proyectochura.view.screens.user.ModifyUser
import com.sandur.proyectochura.view.screens.user.User

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavegation() {
    val navController = rememberNavController()

    ScallfoldGeneral(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.MainScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(700))
            }
        ) {
            composable(AppScreens.MainScreen.route) {
                MainScreen(navController)
            }
            composable(AppScreens.PetCategory.route) {
                PetCategory(navController)
            }
            composable(AppScreens.Shop.route) {
                Shop(navController)
            }
            composable(AppScreens.Paises.route) {
                Buscar(navController)
            }
            composable(AppScreens.Login.route) {
                Login(navController)
            }

            composable(AppScreens.Products.route) { backStackEntry ->
                Products(
                    navController,
                    backStackEntry.arguments?.getString("idcategoria"),
                    backStackEntry.arguments?.getString("nombrecategoria")
                )
            }
            composable(AppScreens.AgregarPais.route) {
                AgregarPais(navController)
            }
            composable(AppScreens.AgregarUser.route) {
                AgregarUser(navController)
            }
            composable(AppScreens.User.route) {
                User(navController)
            }

            // Nueva pantalla para modificar usuario
            composable(
                route = AppScreens.ModifyUser.route,
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                ModifyUser(navController, userId)
            }
        }
    }
}
