package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.report.CreateForm
import com.capstone.ecoreport.ui.auth.LoginScreen
import com.capstone.ecoreport.ui.home.screens.ProfileScreen
import com.capstone.ecoreport.ui.auth.RegisterScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoReportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.Login) }

    when (currentScreen) {
        Screen.Login -> {
            LoginScreen(
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
