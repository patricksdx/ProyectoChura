package com.sandur.proyectochura.utils.navegation

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("main_screen")
    object PetCategory: AppScreens("pet_category")
    object Shop: AppScreens("shop")
    object Insert: AppScreens("insert")
    object Profile: AppScreens("porfile")

    object Products : AppScreens("products/{idcategoria}/{nombrecategoria}") {
        fun createRoute(idcategoria: String, nombrecategoria: String) =
            "products/$idcategoria/$nombrecategoria"
    }
}