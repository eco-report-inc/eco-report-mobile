package com.capstone.ecoreport.data.mlkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.vision.objects.DetectedObject

class ObjectDetectionView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObjectDetectionUI()
        }
    }
}

@Composable
fun ObjectDetectionUI() {
    val viewModel: EcoReportMlModel = viewModel()
    val detectedObjects by viewModel.detectedObjects.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(detectedObjects) { detectedObject ->
                DetectedObjectItem(detectedObject = detectedObject)
            }
        }
    }
}

@Composable
fun DetectedObjectItem(detectedObject: DetectedObject) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Object ID: ${detectedObject.trackingId}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Label: ${detectedObject.labels.firstOrNull()?.text ?: "Unknown"}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Confidence: ${detectedObject.labels.firstOrNull()?.confidence ?: 0.0}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bounding Box: ${detectedObject.boundingBox}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
