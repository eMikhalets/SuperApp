package com.emikhalets.superapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.common.AppLogger
import com.emikhalets.core.common.logi
import com.emikhalets.core.navigation.AppBottomBarItem
import com.emikhalets.core.ui.component.AppScaffold
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.Purple500
import com.emikhalets.superapp.navigation.AppNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logi(TAG, "Created")
        AppLogger.init()

        setContent {
            logi(TAG, "Set Content")
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val systemUiController = rememberSystemUiController()

            SideEffect {
                logi(TAG, "Set Android UI")
                systemUiController.setStatusBarColor(
                    color = Purple500,
                    darkIcons = false
                )
                systemUiController.isNavigationBarVisible = false
            }

            Application(navController, scaffoldState)
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}

@Composable
private fun Application(navHostController: NavHostController, scaffoldState: ScaffoldState) {
    val tag = "Application"
    logi(tag, "Invoke")
    val bottomBarList = remember { mutableStateListOf<AppBottomBarItem>() }
    logi(tag, "Value bottomBarList = [${bottomBarList.joinToString(" ") { it.route }}]")

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AppScaffold(
                navHostController,
                scaffoldState,
                bottomBarList
            ) {
                AppNavHost(navHostController) {
                    if (!bottomBarList.same(it)) {
                        bottomBarList.clear()
                        bottomBarList.addAll(it)
                    }
                }
            }
        }
    }
}

private fun List<AppBottomBarItem>.same(list: List<AppBottomBarItem>): Boolean {
    return this.containsAll(list) && list.containsAll(this)
}
