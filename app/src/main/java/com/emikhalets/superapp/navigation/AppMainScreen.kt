package com.emikhalets.superapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.core.AppScreen

sealed class AppMainScreen(val route: String) {

    object Main : AppMainScreen("app_main"), AppScreen {

        override val icon: ImageVector? = null
    }

    companion object {

        const val APP_NOTES_ROUTE: String = "app_notes_graph"
    }
}
