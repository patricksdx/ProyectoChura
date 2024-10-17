package com.sandur.proyectochura.utils.navegation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("main_screen")
    object PetCategory: AppScreens("pet_category")
}