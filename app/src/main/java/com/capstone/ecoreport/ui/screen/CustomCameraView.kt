package com.capstone.ecoreport.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors
class CustomCameraView(
    context: Context,
    private val onFrameProcessed: (Bitmap) -> Unit
) : FrameLayout(context) {

    private val previewView = PreviewView(context)
    private var cameraProvider: ProcessCameraProvider? = null
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    init {
        addView(previewView)
        previewView.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(context))
    }
    private fun bindPreview() {
        val preview = Preview.Builder().build()

        val camera = cameraProvider?.bindToLifecycle(
            context as LifecycleOwner, cameraSelector, preview
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
        setupImageAnalysis()
    }
    private fun setupImageAnalysis() {
        val executor = Executors.newSingleThreadExecutor()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(executor, { imageProxy ->
            val bitmap = imageProxy.toBitmap()
            // Panggil fungsi untuk pemrosesan gambar dan deteksi objek
            onFrameProcessed(bitmap)
            imageProxy.close()
        })
        cameraProvider?.bindToLifecycle(context as LifecycleOwner, cameraSelector, imageAnalysis)
    }
    // Fungsi ekstensi untuk mengonversi ImageProxy ke Bitmap
    private fun ImageProxy.toBitmap(): Bitmap {
        val yuvBytes = ByteArray(this.planes[0].buffer.remaining())
        this.planes[0].buffer.get(yuvBytes)

        val yuvImage = YuvImage(
            yuvBytes, ImageFormat.NV21, this.width, this.height, null
        )
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
        val bytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
