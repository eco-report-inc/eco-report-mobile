package com.capstone.ecoreport.ui.screen


import android.Manifest
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(navController: NavController) {

    val locationPermissionState: PermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    MainContent(
        hasPermission = locationPermissionState.status.isGranted,
        onRequestPermission = locationPermissionState::launchPermissionRequest
    )
}
@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    if (hasPermission) {
        TrashDetectionScreen()
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}
