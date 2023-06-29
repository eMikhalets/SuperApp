package com.emikhalets.core.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.navigation.AppScreen
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun AppBottomBar(
    navController: NavHostController,
    bottomBarItems: List<AppScreen>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        bottomBarItems.forEach { item ->
            BottomNavigationItem(
                icon = item.icon?.let { { Icon(imageVector = it, contentDescription = null) } }
                    ?: {},
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        // TODO: commented restore state in bottom bar
//                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppBottomBar(
            navController = rememberNavController(),
            bottomBarItems = listOf(
                object : AppScreen {
                    override val route: String = "tasks"
                    override val icon: ImageVector = Icons.Rounded.Task
                },
                object : AppScreen {
                    override val route: String = "notes"
                    override val icon: ImageVector = Icons.Rounded.EditNote
                },
                object : AppScreen {
                    override val route: String = "settings"
                    override val icon: ImageVector = Icons.Rounded.Settings
                }
            )
        )
    }
}