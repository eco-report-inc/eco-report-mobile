package com.capstone.ecoreport.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.capstone.ecoreport.R

object MyIcons {
    val Edit = Icons.Filled.Edit
    val KeyboardArrowRight = Icons.Default.KeyboardArrowRight
    val Summarize = Icons.Filled.Summarize
    val Profile = Icons.Outlined.Person
    val Point = Icons.Filled.Star

    val AppIcon = R.drawable.ic_launcher_background
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class DCodeIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : DCodeIcon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : DCodeIcon()
}