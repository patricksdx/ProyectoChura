package com.sandur.proyectochura.navegation

import ScallfoldGeneral
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.sandur.proyectochura.view.screens.mainScreen.MainScreen
import com.sandur.proyectochura.view.screens.pet.PetCategory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavegation() {
    val navController = rememberNavController()

    ScallfoldGeneral(navController = navController) {
        AnimatedNavHost(
            navController = navController,
            startDestination = AppScreens.MainScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(1000))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(1000))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(1000))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(1000))
            }
        ) {
            composable(AppScreens.MainScreen.route) {
                MainScreen(navController)
            }
            composable(AppScreens.PetCategory.route) {
                PetCategory(navController)
            }
        }
    }
}
