package com.emikhalets.core.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
interface AppBottomBarItem {

    val route: String
    val icon: ImageVector

    companion object {

        fun getInstance(route: String, icon: ImageVector): AppBottomBarItem {
            return object : AppBottomBarItem {
                override val route: String = route
                override val icon: ImageVector = icon
            }
        }
    }
}