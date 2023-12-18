package com.capstone.ecoreport

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.ui.screen.HomeScreen
import com.capstone.ecoreport.ui.screen.LoginScreen
import com.capstone.ecoreport.ui.screen.RegisterScreen

@Composable
fun EcoNavigation(authManager: AuthManager) {
    var currentScreen by remember { mutableStateOf(Screen.Login) }

    LaunchedEffect(authManager.isLoggedIn()) {
        if (authManager.isLoggedIn() && authManager.getAuthToken().isNullOrEmpty()) {
            authManager.clearAuthToken()
            currentScreen = Screen.Login
        } else if (authManager.isLoggedIn()) {
            currentScreen = Screen.HomeScreen
        }
    }
    when (currentScreen) {
        Screen.Login -> {
            LoginScreen(
                onRegisterClicked = {
                    currentScreen = Screen.Register
                },
                onLoginSuccess = {
                    currentScreen = Screen.HomeScreen
                },
                onLoginError = { error ->
                },
                authManager = AuthManager(LocalContext.current)
            )
        }
        Screen.Register -> {
            RegisterScreen(
                context = LocalContext.current,
                onLoginClicked = {
                    currentScreen = Screen.Login
                },
                onRegisterSuccess = {
                    currentScreen = Screen.Login
                },
                onRegisterError = { error ->
                },
                authManager = AuthManager(LocalContext.current)
            )
        }
        Screen.HomeScreen -> {
            HomeScreen(navigateToDetail = {})
        }
    }
}
enum class Screen {
    Login,
    Register,
    HomeScreen,
}