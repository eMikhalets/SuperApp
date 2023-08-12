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
import com.emikhalets.convert.navigation.AppConvertRoute
import com.emikhalets.convert.navigation.appConvertBottomBar
import com.emikhalets.core.ui.BottomBarModel
import com.emikhalets.core.ui.extentions.BoxPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.events.navigation.AppEventsRoute
import com.emikhalets.events.navigation.appEventsBottomBar
import com.emikhalets.finance.navigation.AppFinanceRoute
import com.emikhalets.finance.navigation.appFinanceBottomBar
import com.emikhalets.fitness.navigation.AppFitnessRoute
import com.emikhalets.fitness.navigation.appFitnessBottomBar
import com.emikhalets.medialib.navigation.AppMediaLibRoute
import com.emikhalets.medialib.navigation.appMediaLibBottomBar
import com.emikhalets.notes.navigation.AppNotesRoute
import com.emikhalets.notes.navigation.appNotesBottomBar
import com.emikhalets.superapp.navigation.AppNavHost
import com.emikhalets.superapp.navigation.AppRoute
import com.emikhalets.superapp.navigation.appBottomBar

@Composable
fun AppScreen(navController: NavHostController) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = { AppBottomBarBox(navController) },
        modifier = Modifier.safeDrawingPadding(),
        content = {
            Box(modifier = Modifier.padding(it)) {
                AppNavHost(navController = navController)
            }
        }
    )
}

@Composable
private fun AppBottomBarBox(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarItems = remember { mutableStateListOf<BottomBarModel>() }

    LaunchedEffect(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            AppRoute.BottomBarTrigger -> bottomBarItems.update(appBottomBar)
            AppConvertRoute.BottomBarTrigger -> bottomBarItems.update(appConvertBottomBar)
            AppEventsRoute.BottomBarTrigger -> bottomBarItems.update(appEventsBottomBar)
            AppFinanceRoute.BottomBarTrigger -> bottomBarItems.update(appFinanceBottomBar)
            AppFitnessRoute.BottomBarTrigger -> bottomBarItems.update(appFitnessBottomBar)
            AppMediaLibRoute.BottomBarTrigger -> bottomBarItems.update(appMediaLibBottomBar)
            AppNotesRoute.BottomBarTrigger -> bottomBarItems.update(appNotesBottomBar)
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
    bottomBarItems: List<BottomBarModel>,
) {
    BottomNavigation {
        bottomBarItems.forEach { item ->
            BottomNavigationItem(
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

private fun MutableList<BottomBarModel>.update(items: List<BottomBarModel>) {
    when {
        items.isEmpty() -> this.clear()
        this != items -> {
            this.clear()
            this.addAll(items)
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
                BottomBarModel("tasks", Icons.Rounded.Task),
                BottomBarModel("notes", Icons.Rounded.EditNote),
                BottomBarModel("settings", Icons.Rounded.Settings),
            ),
        )
    }
}