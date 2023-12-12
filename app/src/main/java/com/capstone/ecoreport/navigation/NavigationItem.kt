package com.capstone.ecoreport.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val screen: Screen
)
