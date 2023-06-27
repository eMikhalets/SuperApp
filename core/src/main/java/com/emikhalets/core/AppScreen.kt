package com.emikhalets.core

import androidx.compose.ui.graphics.vector.ImageVector

interface AppScreen {

    val route: String
    val icon: ImageVector?
}