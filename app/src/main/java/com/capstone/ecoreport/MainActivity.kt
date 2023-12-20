package com.capstone.ecoreport

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.ecoreport.data.api.ApiConfig
import com.capstone.ecoreport.data.auth.AuthManager
import com.capstone.ecoreport.data.auth.AuthRepository
import com.capstone.ecoreport.navigation.Screen
import com.capstone.ecoreport.ui.screen.CameraXScreen
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Izin lokasi diberikan
                // Lakukan sesuatu di sini jika diperlukan
            } else {
                // Izin lokasi ditolak
                // Lakukan sesuatu di sini jika diperlukan
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek izin lokasi
        checkLocationPermission()

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

    private fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val permissionStatus = ContextCompat.checkSelfPermission(this, permission)

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            // Izin lokasi belum diberikan, minta izin
            requestPermissionLauncher.launch(permission)
        }
    }
}
