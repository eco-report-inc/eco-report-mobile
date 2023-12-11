package com.capstone.ecoreport.ui.report


import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.capstone.ecoreport.data.ai.TrashDetectionModel
import com.capstone.ecoreport.ui.theme.EcoReportTheme


class CameraPreviewScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoReportTheme {
                CameraPreviewScreenContent()
            }
        }
    }
}

@Composable
fun CameraPreviewScreenContent() {

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    // Inisialisasi model deteksi
    val detectionModel = remember {
        TrashDetectionModel(context = context, objectDetectorListener = null)
    }

    // Jalankan deteksi saat layar ditampilkan
    LaunchedEffect(Unit) {
        detectionModel.setupObjectDetector()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
    }
}