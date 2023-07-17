package com.emikhalets.core.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
interface AppBottomBarItem {

    val route: String
    val icon: ImageVector
}