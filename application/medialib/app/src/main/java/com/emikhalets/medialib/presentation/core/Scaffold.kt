package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.navigation.MediaLibScreen
import com.emikhalets.medialib.presentation.theme.AppColors.textSecondary
import com.emikhalets.medialib.presentation.theme.MediaLibTheme

@Composable
fun MediaLibScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = { MediaLibBottomBar(navController) }
    ) {
        Surface(color = MaterialTheme.colors.surface) {
            Box(modifier = Modifier.padding(it)) {
                content()
            }
        }
    }
}

@Composable
private fun MediaLibBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation {
        MediaLibScreen.getBottomBarItems().forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.iconRes), null) },
                label = { Text(stringResource(item.titleRes)) },
                selected = navBackStackEntry?.destination?.hierarchy
                    ?.any { it.route == item.screen.route } == true,
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.textSecondary,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MediaLibTheme {
        MediaLibScaffold(
            navController = rememberNavController(),
            scaffoldState = rememberScaffoldState()
        ) {
            Text(text = "Test")
        }
    }
}