package com.capstone.ecoreport.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.ecoreport.core.utils.rotateBitmap
import com.capstone.ecoreport.ui.common.CameraState
import com.capstone.ecoreport.ui.viewmodel.TrashDetectionViewModel
import com.google.mlkit.vision.objects.DetectedObject
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.Executor

@Composable
fun TrashDetectionScreen(
    viewModel: TrashDetectionViewModel = koinViewModel()
) {
    val cameraState: CameraState by viewModel.state.collectAsStateWithLifecycle()
    val detectedObjects: List<DetectedObject> by viewModel.detectedObjects.collectAsState()
    val isTrashDetected: Boolean by viewModel.isTrashDetected.collectAsState()
    val isProcessing: Boolean by viewModel.isProcessing.collectAsState()

    CameraContent(
        onPhotoCaptured = viewModel::storePhotoInGallery,
        lastCapturedPhoto = cameraState.capturedImage
    )
    ObjectDetectionOverlay(detectedObjects, Modifier.fillMaxSize(), isTrashDetected, isProcessing)
}
@Composable
private fun ObjectDetectionOverlay(
    detectedObjects: List<DetectedObject>,
    modifier: Modifier,
    isTrashDetected: Boolean,
    isProcessing: Boolean
) {
    Box(modifier = modifier) {
        for (detectedObject in detectedObjects) {
            DrawBoundingBoxAndLabel(detectedObject)
        }
        if (!isTrashDetected && !isProcessing) {
            DrawText("Tidak Ada Tumpukan Sampah", 16.dp, 16.dp)
        } else if (isProcessing) {
            DrawText("Memproses...", 16.dp, 16.dp)
        }
    }
}
@Composable
private fun DrawBoundingBoxAndLabel(detectedObject: DetectedObject) {
    val boundingBox = detectedObject.boundingBox
    val isTrashDetected = isTrash(detectedObject)

    Box(
        modifier = Modifier
            .offset(
                x = boundingBox.left.dp,
                y = boundingBox.top.dp
            )
            .size(
                width = boundingBox.width().dp,
                height = boundingBox.height().dp
            )
    ) {
        // Draw bounding box and label
        DrawBoundingBox(boundingBox, isTrashDetected)
    }
}
private fun isTrash(detectedObject: DetectedObject): Boolean {
    for (label in detectedObject.labels) {
        val text = label.text
        val confidence = label.confidence
        // Sesuaikan dengan kriteria label sampah Anda
        if (text.equals("trash", ignoreCase = true) && confidence > 0.5f) {
            return true
        }
    }
    return false
}
@Composable
private fun DrawBoundingBox(boundingBox: Rect, isTrashDetected: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .border(width = 2.dp, color = Color.Red),
        contentAlignment = Alignment.TopStart
    ) {
        // Calculate position and size based on boundingBox
        val boxModifier = Modifier
            .fillMaxSize()
            .offset(
                x = boundingBox.left.dp,
                y = boundingBox.top.dp
            )
            .size(
                width = boundingBox.width().dp,
                height = boundingBox.height().dp
            )

        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            // Draw bounding box
            if (isTrashDetected) {
                DrawText("Terdeteksi Sampah", 0.dp, -16.dp)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(255, 0, 0, 128))
            )
        }
    }
}
@Composable
private fun DrawText(text: String, xPosition: Dp, yPosition: Dp) {
    Box(
        modifier = Modifier
            .offset(x = xPosition, y = yPosition)
            .background(color = Color.Transparent)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
@Composable
private fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null
) {

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }

    androidx.compose.material.Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            androidx.compose.material.ExtendedFloatingActionButton(
                text = { androidx.compose.material.Text(text = "Take photo") },
                onClick = { capturePhoto(context, cameraController, onPhotoCaptured) },
                icon = {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Camera capture icon"
                    )
                }
            )
        }
    ) { paddingValues: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(android.graphics.Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                }
            )

            if (lastCapturedPhoto != null) {
                LastPhotoPreview(
                    modifier = Modifier.align(alignment = Alignment.BottomStart),
                    lastCapturedPhoto = lastCapturedPhoto
                )
            }
        }
    }
}

private fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image
                .toBitmap()
                .rotateBitmap(image.imageInfo.rotationDegrees)

            onPhotoCaptured(correctedBitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier,
    lastCapturedPhoto: Bitmap
) {

    val capturedPhoto: ImageBitmap = remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }

    androidx.compose.material.Card(
        modifier = modifier
            .size(128.dp)
            .padding(16.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            bitmap = capturedPhoto,
            contentDescription = "Last captured photo",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun Preview_CameraContent() {
    CameraContent(
        onPhotoCaptured = {}
    )
}