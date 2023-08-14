package com.emikhalets.superapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.screen.AppScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                val systemUiColor = MaterialTheme.colors.primary
                val navController = rememberNavController()
                SideEffect {
                    systemUiController.setStatusBarColor(systemUiColor, false)
                    systemUiController.setNavigationBarColor(Color.Black, false)
                }
                AppScreen(navController)
            }
        }
    }
}