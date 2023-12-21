package com.capstone.ecoreport.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.ecoreport.core.utils.rotateBitmap
import com.capstone.ecoreport.ui.common.CameraState
import com.capstone.ecoreport.ui.viewmodel.TrashDetectionViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.Executor
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.capstone.ecoreport.data.mlkit.EcoImageAnalyzer
import com.capstone.ecoreport.data.mlkit.ObjectDetectionViewModel
import com.capstone.ecoreport.utils.BoundingBox
import com.capstone.ecoreport.utils.UIUpdater
import com.google.mlkit.vision.objects.DetectedObject

@Composable
fun TrashDetectionScreen(
    trashDetectionViewModel: TrashDetectionViewModel = koinViewModel(),
    objectViewModel: ObjectDetectionViewModel = koinViewModel()
) {
    val cameraState: CameraState by trashDetectionViewModel.state.collectAsStateWithLifecycle()

    var boundingBoxesState = remember { mutableStateListOf<BoundingBox>() }
    var labelsState = remember { mutableStateListOf<DetectedObject.Label>() }

    CameraContent(
        onPhotoCaptured = trashDetectionViewModel::storePhotoInGallery,
        lastCapturedPhoto = cameraState.capturedImage,
        viewModel = objectViewModel,
        uiUpdater = remember {
            object : UIUpdater {
                override fun addBoundingBox(boundingBox: BoundingBox) {
                    boundingBoxesState.add(boundingBox)
                }

                override fun addLabel(label: DetectedObject.Label) {
                    labelsState.add(label)
                }

            }
        }
    )
}
@Composable
private fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null,
    viewModel: ObjectDetectionViewModel,
    uiUpdater: UIUpdater
) {

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    val cameraExecutor = ContextCompat.getMainExecutor(context)

    var boundingBoxesState = remember { mutableStateListOf<BoundingBox>() }
    var labelsState = remember { mutableStateListOf<DetectedObject.Label>() }


    val uiUpdater = remember { object : UIUpdater{
        override fun addBoundingBox(boundingBox: BoundingBox) {
            boundingBoxesState.add(boundingBox)
        }

        override fun addLabel(label: DetectedObject.Label) {
            labelsState.add(label)
        }

    } }
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
    imageAnalysis.setAnalyzer(cameraExecutor, EcoImageAnalyzer(viewModel, uiUpdater))


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
            Canvas(modifier = Modifier.fillMaxSize()) {
                for (boundingBox in boundingBoxesState) {
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(boundingBox.left, boundingBox.top),
                        size = Size(boundingBox.right - boundingBox.left, boundingBox.bottom - boundingBox.top)
                    )
                }
            }
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