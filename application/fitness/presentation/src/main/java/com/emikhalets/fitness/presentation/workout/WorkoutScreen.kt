package com.emikhalets.fitness.presentation.workout

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.common.formatTime
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.presentation.WorkoutTypeHeader
import com.emikhalets.ui.theme.SuperAppTheme
import java.util.Date

@Composable
fun WorkoutScreen(
    stageType: WorkoutType,
    navigateBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWorkout(stageType)
    }

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            navigateBack()
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
        state = state,
        stageType = stageType,
        onBackClick = navigateBack,
        onRepeatClick = viewModel::updateOnRepeat,
        onDoneClick = viewModel::startTimer
    )
}

@Composable
private fun ScreenContent(
    state: WorkoutState,
    stageType: WorkoutType,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onRepeatClick: (WorkoutEntity) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        WorkoutTypeHeader(
            type = stageType,
            onBackClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        )
        val workoutEntity = state.workout
        if (workoutEntity != null && workoutEntity.reps.isNotEmpty()) {
            Text(
                text = workoutEntity.reps.joinToString("  "),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )
            Text(
                text = state.currentRep.toString(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
            Text(
                text = formatTime(state.timerValue),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
            if (state.isCompleted) {
                Text(
                    text = "Workout completed!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                WorkoutButton(
                    text = "Done",
                    onClick = onDoneClick,
                    modifier = Modifier.padding(8.dp),
                    enabled = !state.isTimerActive
                )
                WorkoutButton(
                    text = "On Repeat",
                    onClick = { onRepeatClick(workoutEntity) },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.WorkoutButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
            .width(170.dp)
            .align(Alignment.CenterHorizontally)
            .height(48.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SuperAppTheme {
        ScreenContent(
            state = WorkoutState(
                workout = WorkoutEntity(
                    0, 6, Date().time, WorkoutType.PRESS,
                    WorkoutDoneType.NOT_DONE, listOf(35, 35, 45, 45, 35, 35, 30, 100)
                ),
                currentRep = 45,
                timerValue = 90,
                isCompleted = false,
            ),
            stageType = WorkoutType.PRESS,
            onBackClick = {},
            onRepeatClick = {},
            onDoneClick = {}
        )
    }
}