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
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.report.CreateForm
import com.capstone.ecoreport.ui.auth.LoginScreen
import com.capstone.ecoreport.ui.home.screens.ProfileScreen
import com.capstone.ecoreport.ui.auth.RegisterScreen
import com.capstone.ecoreport.ui.home.screens.HomeScreen
import com.capstone.ecoreport.ui.home.screens.MapsScreen

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
            authManager.clearAuthToken()
            currentScreen = Screen.Login
        } else if (authManager.isLoggedIn()) {
            currentScreen = Screen.HomeScreen
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
        Screen.CreateForm -> {
            CreateForm()
        }
        Screen.ProfileScreen -> {
            ProfileScreen()
        }
        Screen.HomeScreen -> {
            HomeScreen(
                onProfileClicked = {
                    currentScreen = Screen.ProfileScreen
                },
                onMapClicked = {
                    currentScreen = Screen.MapsScreen
                }
            )
        }
        Screen.MapsScreen -> {
            MapsScreen()
        }
    }
}
enum class Screen {
    Login,
    Register,
    CreateForm,
    ProfileScreen,
    HomeScreen,
    MapsScreen
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
