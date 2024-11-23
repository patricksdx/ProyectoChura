package com.sandur.proyectochura.utils.navegation

import ScallfoldGeneral
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sandur.proyectochura.view.screens.paises.Buscar
import com.sandur.proyectochura.view.screens.mainScreen.MainScreen
import com.sandur.proyectochura.view.screens.paises.AgregarPais
import com.sandur.proyectochura.view.screens.pet.PetCategory
import com.sandur.proyectochura.view.screens.profile.Profile
import com.sandur.proyectochura.view.screens.shop.Products
import com.sandur.proyectochura.view.screens.shop.Shop
import com.sandur.proyectochura.view.screens.profile.AgregarUser

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
            composable(AppScreens.Profile.route) {
                Profile(navController)
            }

            // NO PRINCIPALES
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
        }
    }
}