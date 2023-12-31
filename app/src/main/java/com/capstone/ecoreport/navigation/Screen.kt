package com.capstone.ecoreport.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Maps : Screen("maps")
    object Profile : Screen("profile")
    object Detail : Screen("home/{dummyId}") {
        fun createRoute(dummyId: Int) = "home/$dummyId"
    }
    object EditProfile : Screen("editProfile")
    object CameraX : Screen("cameraX")
    object Register : Screen("register")
    object Login : Screen("login")
    object Location : Screen("Location")

}