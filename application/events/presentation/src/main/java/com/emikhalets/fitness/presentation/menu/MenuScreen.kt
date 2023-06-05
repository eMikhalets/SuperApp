package com.emikhalets.fitness.presentation.menu

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.fitness.presentation.R
import com.emikhalets.ui.component.AppButton
import com.emikhalets.ui.component.ChildScreenBox
import com.emikhalets.ui.theme.AppTheme

@Composable
fun MenuScreen(
    navigateToPress: () -> Unit,
    navigateToPullUp: () -> Unit,
    navigateToPushUp: () -> Unit,
    navigateToSquat: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: MenuViewModel,
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
    ChildScreenBox(onBackClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (isInitialized) {
                MenuButton(
                    text = stringResource(R.string.fitness_press),
                    onClick = onPressClick
                )
                Spacer(modifier = Modifier.height(32.dp))
                MenuButton(
                    text = stringResource(R.string.fitness_pull_ups),
                    onClick = onPullUpClick
                )
                Spacer(modifier = Modifier.height(32.dp))
                MenuButton(
                    text = stringResource(R.string.fitness_push_ups),
                    onClick = onPushUpClick
                )
                Spacer(modifier = Modifier.height(32.dp))
                MenuButton(
                    text = stringResource(R.string.fitness_squats),
                    onClick = onSquatClick
                )
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.fitness_app_not_inited))
                }
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
    AppButton(
        text = text,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(48.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
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