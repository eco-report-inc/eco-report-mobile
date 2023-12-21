package com.capstone.ecoreport.utils

import com.google.mlkit.vision.objects.DetectedObject

interface UIUpdater {
    fun addBoundingBox(boundingBox: BoundingBox)
    fun addLabel(label: DetectedObject.Label)
}
