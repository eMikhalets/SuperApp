package com.emikhalets.superapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import com.emikhalets.core.common.AppLogger
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.superapp.screen.AppScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logi(TAG, "Created")
        AppLogger.init()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                logi(TAG, "Set Content")
                val systemUiController = rememberSystemUiController()
                val systemUiColor = MaterialTheme.colors.primary

                SideEffect {
                    logi(TAG, "Set Android UI")
                    systemUiController.setStatusBarColor(
                        color = systemUiColor,
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(
                        color = systemUiColor,
                        darkIcons = false
                    )
                }

                AppScreen()
            }
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}