package com.capstone.ecoreport.ui.screen

import android.util.Log
import android.util.Size
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import java.util.concurrent.Executors

@Composable
fun TrashDetectionScreen() {
    var isTrashDetected by remember { mutableStateOf(false) }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    // Fungsi untuk menangani logika navigasi
    fun navigateToCreateForm() {
        // Implementasi navigasi ke halaman CreateForm disini
    }

    // Logika untuk mendeteksi sampah menggunakan TFLite
    // ...

    // Logika untuk inisialisasi kamera
    DisposableEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(LocalContext.current)

        cameraProviderFuture.addListener({
            // Setelah kamera diinisialisasi, bind use case
            cameraProvider = cameraProviderFuture.get()

            bindCameraUseCases { imageProxy ->
                // Logika untuk mendeteksi sampah dari imageProxy
                // ...

                // Set isTrashDetected berdasarkan hasil deteksi
                isTrashDetected = true

                // Navigasi ke halaman CreateForm jika sampah terdeteksi
                if (isTrashDetected) {
                    navigateToCreateForm()
                }
            }
        }, ContextCompat.getMainExecutor(LocalContext.current))

        onDispose {
            // Saat komponen dihancurkan, hapus use case
            cameraProvider?.unbindAll()
        }
    }

    BackHandler {
        // Logika yang dijalankan ketika tombol back ditekan
        // ...
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Teks header
        Text("Arahkan sampah ke kamera", style = MaterialTheme.typography.h5)

        // Kotak viewfinder (gantilah dengan implementasi kamera yang sesuai)
        Box(
            modifier = Modifier
                .size(240.dp)
                .background(Color.Black)
                .border(2.dp, Color.White)
                .padding(4.dp)
        ) {
            // PreviewView dari CameraX
            PreviewView(LocalContext.current).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }.also { previewView ->
                AndroidView({ previewView }) { view ->
                    // Hubungkan PreviewView dengan CameraX
                    val cameraExecutor = Executors.newSingleThreadExecutor()

                    val preview = Preview.Builder()
                        .setTargetResolution(Size(640, 480))
                        .build()
                        .also {
                            it.setSurfaceProvider(view.surfaceProvider)
                        }

                    imageCapture = ImageCapture.Builder().build()

                    try {
                        // Unbind semua use case sebelumnya
                        cameraProvider?.unbindAll()

                        // Bind use case baru
                        cameraProvider?.bindToLifecycle(
                            LocalContext.current as LifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        Log.e("CameraX", "Use case binding failed", e)
                    }
                }
            }
        }

        // Teks indikator
        Text(
            text = if (isTrashDetected) "Sampah telah terdeteksi" else "Sampah belum terdeteksi",
            color = if (isTrashDetected) Color.Green else Color.Red,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun TrashDetectionScreenPreview() {
    EcoReportTheme {
        TrashDetectionScreen()
    }
}