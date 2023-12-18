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
                }
            }
        }
    }
}




