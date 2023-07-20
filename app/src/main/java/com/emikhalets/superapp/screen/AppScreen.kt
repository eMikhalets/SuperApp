package com.emikhalets.superapp.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.common.classNames
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.navigation.AppNavHost

private const val TAG = "App"

@Composable
fun AppScreen() {
    logi(TAG, "Invoke")

    val navController = rememberNavController()
    val bottomBarList = remember { mutableStateListOf<AppBottomBarItem>() }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            if (bottomBarList.isNotEmpty()) AppBottomBar(navController, bottomBarList)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            AppNavHost(
                navController = navController,
                bottomBarList = { newList ->
                    if (!bottomBarList.same(newList)) {
                        bottomBarList.clear()
                        bottomBarList.addAll(newList)
                    }
                }
            )
        }
    }
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    bottomBarItems: List<AppBottomBarItem>,
) {
    logi("$TAG.AppBottomBar", "Invoke: items = [${bottomBarItems.classNames}]")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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

private fun List<AppBottomBarItem>.same(list: List<AppBottomBarItem>): Boolean {
    return this.containsAll(list) && list.containsAll(this)
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBar() {
    AppTheme {
        AppBottomBar(
            navController = rememberNavController(),
            bottomBarItems = listOf(
                object : AppBottomBarItem {
                    override val route: String = "tasks"
                    override val icon: ImageVector = Icons.Rounded.Task
                },
                object : AppBottomBarItem {
                    override val route: String = "notes"
                    override val icon: ImageVector = Icons.Rounded.EditNote
                },
                object : AppBottomBarItem {
                    override val route: String = "settings"
                    override val icon: ImageVector = Icons.Rounded.Settings
                }
            )
        )
    }
}