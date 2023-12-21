package com.capstone.ecoreport.data.mlkit

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions

class ObjectDetectionViewModel : ViewModel() {
    private val localModel = LocalModel.Builder()
        .setAssetFilePath("best_float32.tflite")
        .build()

    private val customObjectDetectorOptions =
        CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.5f)
            .setMaxPerObjectLabelCount(3)
            .build()

    private val objectDetector = ObjectDetection.getClient(customObjectDetectorOptions)

    fun processImage(bitmap: Bitmap, rotationDegrees: Int): List<DetectedObject> {
        val image = InputImage.fromBitmap(bitmap, rotationDegrees)
        return objectDetector.process(image).addOnSuccessListener { results ->
            // Handle the results as needed
            // ...
        }.addOnFailureListener { e ->
            // Handle failure
            // ...
        }.result ?: emptyList()
    }
}
