package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.screen.HomeScreen
import com.capstone.ecoreport.ui.screen.LoginScreen
import com.capstone.ecoreport.ui.screen.MapsScreen
import com.capstone.ecoreport.ui.screen.RegisterScreen

class MainActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager = AuthManager(this)
        setContent {
            EcoReportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EcoReport()
                    AppNavigation(authManager)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(authManager: AuthManager) {
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
