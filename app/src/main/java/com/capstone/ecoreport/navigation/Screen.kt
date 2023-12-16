package com.capstone.ecoreport.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Maps : Screen("maps")
    object Profile : Screen("profile")
    object Detail : Screen("home/{dummyId}") {
        fun createRoute(dummyId: Int) = "home/$dummyId"
    }
    object Login : Screen("login")
    object Register : Screen("register")
}

