package com.emikhalets.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface AppScreen {

    val route: String
    val icon: ImageVector?
}