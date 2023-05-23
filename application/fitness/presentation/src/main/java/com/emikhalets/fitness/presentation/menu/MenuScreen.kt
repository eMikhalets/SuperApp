package com.emikhalets.fitness.presentation.menu

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.ui.theme.SuperAppTheme

@Composable
fun MenuScreen(
    navigateToPress: () -> Unit,
    navigateToPullUp: () -> Unit,
    navigateToPushUp: () -> Unit,
    navigateToSquat: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (!state.workoutInitialized) {
            viewModel.initWorkouts()
        }
    }

    LaunchedEffect(state.error) {
        val message = state.error
        if (message != null) {
            Toast.makeText(context, message.asString(), Toast.LENGTH_SHORT).show()
            viewModel.dropError()
        }
    }

    ScreenContent(
        isInitialized = state.workoutInitialized,
        onPressClick = navigateToPress,
        onPullUpClick = navigateToPullUp,
        onPushUpClick = navigateToPushUp,
        onSquatClick = navigateToSquat,
        onBackClick = navigateBack,
    )
}

@Composable
private fun ScreenContent(
    isInitialized: Boolean,
    onPressClick: () -> Unit,
    onPullUpClick: () -> Unit,
    onPushUpClick: () -> Unit,
    onSquatClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isInitialized) {
            MenuButton(
                text = "Press",
                onClick = onPressClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp)
            )
            MenuButton(
                text = "PullUp",
                onClick = onPullUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
            MenuButton(
                text = "PushUp",
                onClick = onPushUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
            MenuButton(
                text = "Squat",
                onClick = onSquatClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
            MenuButton(
                text = "Back",
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Application not initialized")
            }
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SuperAppTheme {
        ScreenContent(
            isInitialized = true,
            onPressClick = {},
            onPullUpClick = {},
            onPushUpClick = {},
            onSquatClick = {},
            onBackClick = {},
        )
    }
}