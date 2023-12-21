package com.capstone.ecoreport.data.mlkit

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.capstone.ecoreport.utils.BoundingBox
import com.capstone.ecoreport.utils.UIUpdater
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject

class EcoImageAnalyzer(private val viewModel: ObjectDetectionViewModel, private val uiUpdater: UIUpdater) : ImageAnalysis.Analyzer {

    @ExperimentalGetImage override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        mediaImage?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            val detectedObjects = viewModel.processImage(
                bitmap = imageProxy.toBitmap(),
                rotationDegrees = imageProxy.imageInfo.rotationDegrees
            )
            handleDetectionResults(detectedObjects)

            // Melepas sumber daya gambar setelah deteksi selesai
            imageProxy.close()
        }
    }
    private fun handleDetectionResults(results: List<DetectedObject>) {
        for (detectedObject in results) {
            val boundingBox = detectedObject.boundingBox
            val trackingId = detectedObject.trackingId

            // Menangani setiap label yang terdeteksi pada objek
            for (label in detectedObject.labels) {
                val text = label.text
                val index = label.index
                val confidence = label.confidence

                // Tampilkan bounding box di UI
                val uiBoundingBox = BoundingBox(
                    boundingBox.left.toFloat(),
                    boundingBox.top.toFloat(),
                    boundingBox.right.toFloat(),
                    boundingBox.bottom.toFloat()
                )

                // Tampilkan label di UI
                val uiLabel = DetectedObject.Label(
                    text,
                    index.toFloat(),
                    confidence.toInt()
                )

                // Tambahkan bounding box dan label ke UI
                uiUpdater.addBoundingBox(uiBoundingBox)
                uiUpdater.addLabel(uiLabel)
            }
        }
    }
}
