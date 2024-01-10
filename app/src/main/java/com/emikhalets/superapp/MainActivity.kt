package com.emikhalets.superapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.convert.navigation.AppConvertRoute
import com.emikhalets.convert.navigation.appConvertBottomBar
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.core.ui.extentions.BoxPreview
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.navigation.AppNavHost
import com.emikhalets.superapp.navigation.AppRoute
import com.emikhalets.superapp.navigation.appBottomBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                HostScreen()
            }
        }
    }
}

@Composable
private fun HostScreen() {
    val systemUiController = rememberSystemUiController()
    val systemUiColor = MaterialTheme.colorScheme.primary
    val navController = rememberNavController()

    SideEffect {
        systemUiController.setStatusBarColor(systemUiColor, false)
        systemUiController.setNavigationBarColor(Color.Black, false)
    }

    Scaffold(
        bottomBar = { AppNavigationBox(navController) },
        content = {
            Box(modifier = Modifier.padding(it)) {
                AppNavHost(navController = navController)
            }
        }
    )
}

@Composable
private fun AppNavigationBox(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = remember { mutableStateListOf<BottomBarModel>() }

    LaunchedEffect(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            AppRoute.BottomBarTrigger -> items.update(appBottomBar)
            AppConvertRoute.BottomBarTrigger -> items.update(appConvertBottomBar)
        }
    }

    if (items.isNotEmpty()) {
        AppNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
            items = items
        )
    }
}

@Composable
private fun AppNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<BottomBarModel>,
) {
    NavigationBar {
        for (item in items) {
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    if (item.route != currentDestination?.route) {
                        navController.navigate(item.route) {
                            val id = navController.graph.findStartDestination().id
                            popUpTo(id) { saveState = true }
                            launchSingleTop = true
                        }
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
        AppNavigationBar(
            navController = rememberNavController(),
            currentDestination = rememberNavController().currentDestination,
            items = listOf(
                BottomBarModel("tasks", Icons.Rounded.Task),
                BottomBarModel("notes", Icons.Rounded.EditNote),
                BottomBarModel("settings", Icons.Rounded.Settings),
            ),
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        HostScreen()
    }
}