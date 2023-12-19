package com.capstone.ecoreport.data.mlkit

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EcoReportMlModel : ViewModel() {

    private val objectDetector = ObjectDetection.getClient(
        ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
    )

    private val _detectedObjects = MutableStateFlow<List<DetectedObject>>(emptyList())
    val detectedObjects: StateFlow<List<DetectedObject>> = _detectedObjects

    fun processImage(image: InputImage) {
        objectDetector.process(image)
            .addOnSuccessListener { detectedObjects ->
                _detectedObjects.value = detectedObjects
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.e(TAG, "Tidak Ada Sampah ${e.message}", e)
            }
    }

    override fun onCleared() {
        super.onCleared()
        objectDetector.close()
    }
}


