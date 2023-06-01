package com.emikhalets.fitness.presentation.stages

import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.presentation.WorkoutStagesList
import com.emikhalets.fitness.presentation.WorkoutTypeHeader
import com.emikhalets.ui.theme.AppTheme
import java.util.Date

@Composable
fun StagesScreen(
    stageType: WorkoutType,
    navigateBack: () -> Unit,
    navigateToWorkout: (WorkoutType) -> Unit,
    viewModel: StagesViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWorkout(stageType)
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
        onWorkoutClick = { navigateToWorkout(stageType) },
        onDoneTypeChange = viewModel::updateWorkout
    )
}

@Composable
private fun ScreenContent(
    state: StagesState,
    stageType: WorkoutType,
    onBackClick: () -> Unit,
    onWorkoutClick: () -> Unit,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        WorkoutTypeHeader(
            type = stageType,
            onBackClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            enabled = state.isNeedWorkout,
            onClick = onWorkoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Workout".uppercase(),
                fontSize = 18.sp,
            )
        }
        WorkoutStagesList(
            workoutList = state.workoutList,
            onDoneTypeChange = onDoneTypeChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = StagesState(
                workoutList = listOf(
                    WorkoutEntity(
                        0, 1, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.DONE, listOf(1, 2, 3, 4, 5)
                    ),
                    WorkoutEntity(
                        0, 2, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.DONE, listOf(1, 2, 3, 4, 5)
                    ),
                    WorkoutEntity(
                        0, 3, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.DONE, listOf(1, 2, 3, 4, 5)
                    ),
                    WorkoutEntity(
                        0, 4, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.REPEAT, listOf(1, 2, 3, 4, 5)
                    ),
                    WorkoutEntity(
                        0, 5, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.NOT_DONE, listOf(1, 2, 3, 4, 5)
                    ),
                    WorkoutEntity(
                        0, 6, Date().time, WorkoutType.PRESS,
                        WorkoutDoneType.NOT_DONE, listOf(35, 35, 45, 45, 35, 35, 30, 100)
                    ),
                )
            ),
            stageType = WorkoutType.PRESS,
            onBackClick = {},
            onWorkoutClick = {},
            onDoneTypeChange = {}
        )
    }
}