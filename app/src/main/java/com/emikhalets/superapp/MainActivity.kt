package com.emikhalets.superapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.superapp.ui.extentions.ScreenPreview
import com.emikhalets.core.superapp.ui.theme.AppTheme
import com.emikhalets.superapp.core.common.model.AppOrientationType
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                HostScreen(::onSetScreenOrientation)
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun onSetScreenOrientation(type: AppOrientationType) {
        requestedOrientation = when (type) {
            AppOrientationType.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            AppOrientationType.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun HostScreen(
    onSetScreenOrientation: (AppOrientationType) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val systemUiColor = MaterialTheme.colorScheme.primary
    val navController = rememberNavController()

    SideEffect {
        systemUiController.setStatusBarColor(systemUiColor, false)
        systemUiController.setNavigationBarColor(Color.Black, false)
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding()
    ) {
        Box {
            AppNavHost(
                navController = navController,
                onSetScreenOrientation = onSetScreenOrientation
            )
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        HostScreen {}
    }
}