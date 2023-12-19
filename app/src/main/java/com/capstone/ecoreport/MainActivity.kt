package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.ecoreport.navigation.Screen
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.screen.CreateFormScreen
import com.capstone.ecoreport.ui.screen.HomeScreen
import com.capstone.ecoreport.ui.screen.LoginScreen
import com.capstone.ecoreport.ui.screen.MapsScreen
import com.capstone.ecoreport.ui.screen.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenWithoutBottomBarList = listOf(
            Screen.Detail.route,
            Screen.CameraX.route,
            Screen.EditProfile.route,
            Screen.Login.route,
            Screen.Register.route
        )

        setContent {
            EcoReportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EcoReport(screenWithoutBottomBarList = screenWithoutBottomBarList)
                }
            }
        }
    }
}
