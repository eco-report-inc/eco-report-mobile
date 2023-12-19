package com.capstone.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.capstone.ecoreport.data.api.ApiConfig
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.navigation.Screen
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiConfig.getApiService()
        val authManager = AuthManager(applicationContext)
        val authRepository = AuthRepository(apiService, authManager)
        val viewModel = AuthViewModel(authRepository, authManager)

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
                    EcoReport(
                        screenWithoutBottomBarList = screenWithoutBottomBarList,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
