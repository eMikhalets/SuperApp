package com.emikhalets.medialib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.core.MediaLibScaffold
import com.emikhalets.medialib.presentation.navigation.MediaLibNavGraph
import com.emikhalets.medialib.presentation.theme.MediaLibTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaLibTheme {
                MediaLib()
            }
        }
    }
}

@Composable
fun MediaLib() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    MediaLibScaffold(navController, scaffoldState) {
        MediaLibNavGraph(navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    MediaLibTheme {
        MediaLib()
    }
}