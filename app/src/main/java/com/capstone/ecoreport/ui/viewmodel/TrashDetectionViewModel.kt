package com.capstone.ecoreport.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ecoreport.data.usecases.SavePhotoToGalleryUseCase
import com.capstone.ecoreport.ui.common.CameraState
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TrashDetectionViewModel(
    private val savePhotoToGalleryUseCase: SavePhotoToGalleryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    private val _detectedObjects = MutableStateFlow<List<DetectedObject>>(emptyList())
    val detectedObjects = _detectedObjects.asStateFlow()

    // Tambahkan variabel state untuk menyimpan status deteksi sampah
    private val _isTrashDetected = MutableStateFlow(false)
    val isTrashDetected = _isTrashDetected.asStateFlow()

    fun storePhotoInGallery(bitmap: Bitmap) {
        viewModelScope.launch {
            savePhotoToGalleryUseCase.call(bitmap)
            updateCapturedPhotoState(bitmap)
        }
    }

    private fun updateCapturedPhotoState(updatedPhoto: Bitmap?) {
        _state.value.capturedImage?.recycle()
        _state.value = _state.value.copy(capturedImage = updatedPhoto)
    }

    private fun processImage(bitmap: Bitmap) {
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
                        launch(Dispatchers.Main) {
                            _detectedObjects.value = detectedObjects
                            handleDetectedObjects(detectedObjects)
                        }
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun handleDetectedObjects(detectedObjects: List<DetectedObject>) {
        var isTrashDetected = false

        for (detectedObject in detectedObjects) {
            val boundingBox = detectedObject.boundingBox
            val trackingId = detectedObject.trackingId

            for (label in detectedObject.labels) {
                val text = label.text
                val index = label.index
                val confidence = label.confidence

                processLabelInfo(text, index, confidence)

                // Check if the detected object is trash (customize based on your label criteria)
                if (text.equals("trash", ignoreCase = true) && confidence > 0.5f) {
                    isTrashDetected = true
                }
            }
        }

        // Update the state based on trash detection
        _isTrashDetected.value = isTrashDetected
    }

    private fun processLabelInfo(text: String?, index: Int, confidence: Float) {
        println("Label: $text, Index: $index, Confidence: $confidence")
    }

    override fun onCleared() {
        _state.value.capturedImage?.recycle()
        super.onCleared()
    }
}