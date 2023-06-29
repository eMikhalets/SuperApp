package com.emikhalets.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface AppBottomBarItem {

    val route: String
    val icon: ImageVector
}