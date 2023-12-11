package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.ecoreport.ui.auth.AuthManager
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.report.CreateForm
import com.capstone.ecoreport.ui.auth.LoginScreen
import com.capstone.ecoreport.ui.home.screens.ProfileScreen
import com.capstone.ecoreport.ui.auth.RegisterScreen

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
            // Token tidak valid atau tidak ada, lakukan logout dan tampilkan layar login
            authManager.clearAuthToken()
            currentScreen = Screen.Login
        } else if (authManager.isLoggedIn()) {
            // Login berhasil dan token ada, arahkan ke layar profil
            currentScreen = Screen.ProfileScreen
        }
    }
    when (currentScreen) {
        Screen.Login -> {
            LoginScreen(
                context = LocalContext.current,
                onRegisterClicked = {
                    currentScreen = Screen.Register
                },
                onLoginSuccess = {
                    currentScreen = Screen.ProfileScreen
                },
                onLoginError = { error ->
                    // Handle login error, if needed
                }
            )
        }
        Screen.Register -> {
            RegisterScreen(
                context = LocalContext.current,
                onLoginClicked = {
                    currentScreen = Screen.Login
                },
                onRegisterSuccess = {
                    // Handle registration success
                    currentScreen = Screen.Login
                },
                onRegisterError = { error ->
                    // Handle registration error, if needed
                }
            )
        }
        Screen.CreateForm -> {
            CreateForm()
        }
        Screen.ProfileScreen -> {
            ProfileScreen()
        }
    }
}


enum class Screen {
    Login,
    Register,
    CreateForm,
    ProfileScreen
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcoReportTheme {
        Greeting("Android")
    }
}
