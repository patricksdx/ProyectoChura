package com.sandur.proyectochura.utils.navegation

sealed class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object PetCategory : AppScreens("pet_category")
    object Shop : AppScreens("shop")
    object Paises : AppScreens("paises")
    object Login : AppScreens("login")

    object Products : AppScreens("products/{idcategoria}/{nombrecategoria}") {
        fun createRoute(idcategoria: String, nombrecategoria: String) =
            "products/$idcategoria/$nombrecategoria"
    }
    object AgregarPais : AppScreens("AgregarPais")
    object User : AppScreens("User")
    object AgregarUser : AppScreens("AgregarUser")

    // Nueva pantalla para modificar usuario
    object ModifyUser : AppScreens("modify_user/{userId}") {
        fun createRoute(userId: Int) = "modify_user/$userId"
    }
}
