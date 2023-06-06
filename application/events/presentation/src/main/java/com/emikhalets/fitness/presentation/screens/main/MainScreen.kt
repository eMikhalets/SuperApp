package com.emikhalets.fitness.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.fitness.presentation.screens.main.MainContract.Action
import com.emikhalets.fitness.presentation.screens.main.MainContract.Effect
import com.emikhalets.ui.component.ChildScreenBox
import com.emikhalets.ui.theme.AppTheme

@Composable
fun MainScreen(
    navigateToEventsList: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: MainViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.CreateDefaultAlarms)
    }

    LaunchedEffect(state.isAlarmsCreated) {
        if (state.isAlarmsCreated) navigateToEventsList()
    }

    LaunchedEffect(effect) {
        when (val effectValue = effect) {
            is Effect.ErrorDialog -> errorMessage = effectValue.message?.asString(context) ?: ""
            null -> errorMessage = ""
        }
    }

    ScreenContent(onBackClick = navigateBack)

    if (errorMessage.isNotEmpty()) {
        // TODO show dialog
    }
}

@Composable
private fun ScreenContent(onBackClick: () -> Unit) {
    ChildScreenBox(onBackClick, stringResource(com.emikhalets.common.R.string.app_events)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(com.emikhalets.common.R.string.app_initialization),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(onBackClick = {})
    }
}