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
import com.capstone.ecoreport.ui.theme.screen.CreateForm
import com.capstone.ecoreport.ui.theme.screen.LoginScreen
import com.capstone.ecoreport.ui.theme.screen.RegisterScreen


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
                    // Handle login success, if needed
                    currentScreen = Screen.CreateForm
                },
                onLoginError = { error ->
                    // Handle login error, if needed
                }
            )
        }
        Screen.Register -> {
            RegisterScreen {
                currentScreen = Screen.Login
            }
        }
        Screen.CreateForm -> {
            CreateForm()
        }
    }
}
enum class Screen {
    Login,
    Register,
    CreateForm
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
