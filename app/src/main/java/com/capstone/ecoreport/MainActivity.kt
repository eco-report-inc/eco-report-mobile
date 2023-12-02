package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.theme.screen.CreateFormScreen
import com.capstone.ecoreport.ui.theme.screen.EditProfileScreen
import com.capstone.ecoreport.ui.theme.screen.LoginScreen
import com.capstone.ecoreport.ui.theme.screen.MapsScreen
import com.capstone.ecoreport.ui.theme.screen.ProfileScreen
import com.capstone.ecoreport.ui.theme.screen.RegisterScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoReportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapsScreen()
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
            LoginScreen {
                currentScreen = Screen.Register
            }
        }
        Screen.Register -> {
            RegisterScreen {
                currentScreen = Screen.Login
            }
        }
        Screen.CreateForm -> {
            CreateFormScreen()
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
