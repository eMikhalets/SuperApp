package com.emikhalets.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

fun List<AppBottomBarItem>.classNames(): String {
    return joinToString(", ") { it::class.simpleName ?: "-" }
}

interface AppBottomBarItem {

    val route: String
    val icon: ImageVector
}