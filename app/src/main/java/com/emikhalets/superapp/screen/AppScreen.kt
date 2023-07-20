package com.emikhalets.superapp.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.common.classNames
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.AppNotesDestination
import com.emikhalets.notes.applicationNotesBottomBar
import com.emikhalets.superapp.navigation.AppMainDestination
import com.emikhalets.superapp.navigation.AppNavHost

private const val TAG = "App"

@Composable
fun AppScreen() {
    logi(TAG, "Invoke")

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { AppBottomBarBox(navController) },
        modifier = Modifier.safeDrawingPadding()
    ) {
        Box(modifier = Modifier.padding(it)) {
            AppNavHost(navController = navController)
        }
    }
}

@Composable
private fun AppBottomBarBox(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarItems = remember { mutableStateListOf<AppBottomBarItem>() }

    LaunchedEffect(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            AppMainDestination.Main -> {
                if (bottomBarItems.isNotEmpty()) bottomBarItems.clear()
            }

            AppNotesDestination.BottomBarTrigger -> {
                if (bottomBarItems != applicationNotesBottomBar) {
                    bottomBarItems.clear()
                    bottomBarItems.addAll(applicationNotesBottomBar)
                }
            }
        }
    }

    if (bottomBarItems.isNotEmpty()) {
        AppBottomBar(
            navController = navController,
            currentDestination = currentDestination,
            bottomBarItems = bottomBarItems
        )
    }
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    bottomBarItems: List<AppBottomBarItem>,
) {
    logi("$TAG.AppBottomBar", "Invoke: items = ${bottomBarItems.classNames}")

    BottomNavigation {
        bottomBarItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        // TODO: commented restore state in bottom bar
                        // restoreState = true
                    }
                }
            )
        }
    }
}

@BoxPreview
@Composable
private fun PreviewBottomBar() {
    AppTheme {
        AppBottomBar(
            navController = rememberNavController(),
            currentDestination = rememberNavController().currentDestination,
            bottomBarItems = listOf(
                AppBottomBarItem.getInstance("tasks", Icons.Rounded.Task),
                AppBottomBarItem.getInstance("notes", Icons.Rounded.EditNote),
                AppBottomBarItem.getInstance("settings", Icons.Rounded.Settings),
            ),
        )
    }
}