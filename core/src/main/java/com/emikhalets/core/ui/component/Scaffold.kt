package com.emikhalets.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.AppBottomBarItem
import com.emikhalets.core.ui.theme.AppTheme

@Composable
@NonRestartableComposable
fun AppScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    bottomBarList: List<AppBottomBarItem> = emptyList(),
    content: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            if (bottomBarList.isNotEmpty()) AppBottomBar(navController, bottomBarList)
        }
    ) {
        Surface(color = MaterialTheme.colors.surface) {
            Box(modifier = Modifier.padding(it)) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppScaffold(
            navController = rememberNavController(),
            scaffoldState = rememberScaffoldState()
        ) {
            Text(text = "Test")
        }
    }
}