package com.capstone.ecoreport.data.mlkit

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ObjectDetectionViewModel : ViewModel() {

    fun processImage(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val localModel = LocalModel.Builder()
            .setAssetFilePath("best_float32.tflite")
            .build()

        val options = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.5f)
            .setMaxPerObjectLabelCount(3)
            .build()

        val customObjectDetector = ObjectDetection.getClient(options)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                customObjectDetector.process(image)
                    .addOnSuccessListener { detectedObjects ->
                        // Handle the detected objects on the main thread
                        launch(Dispatchers.Main) {
                            handleDetectedObjects(detectedObjects)
                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle error
                        e.printStackTrace()
                    }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
    private fun handleDetectedObjects(detectedObjects: List<DetectedObject>) {
        // Process and handle the detected objects
        for (detectedObject in detectedObjects) {
            val boundingBox = detectedObject.boundingBox
            val trackingId = detectedObject.trackingId
            for (label in detectedObject.labels) {
                val text = label.text
                val index = label.index
                val confidence = label.confidence
                // Handle the label information as needed
            }
        }
    }
}
