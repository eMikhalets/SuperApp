package com.emikhalets.fitness.presentation.stages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.ui.component.ChildScreenBox
import com.emikhalets.ui.theme.AppTheme
import java.util.Date

@Composable
fun StagesScreen(
    stageType: WorkoutType,
    navigateBack: () -> Unit,
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
        onDoneTypeChange = viewModel::updateWorkout
    )
}

@Composable
private fun ScreenContent(
    state: StagesState,
    stageType: WorkoutType,
    onBackClick: () -> Unit,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
) {
    ChildScreenBox(onBackClick = onBackClick, label = stageType.title) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 52.dp)
        ) {
            WorkoutStagesList(
                workoutList = state.workoutList,
                onDoneTypeChange = onDoneTypeChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun WorkoutStagesList(
    workoutList: List<WorkoutEntity>,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .border(width = 2.dp, color = MaterialTheme.colors.onBackground)
    ) {
        items(workoutList) { workout ->
            WorkoutStageBox(
                workout = workout,
                onDoneTypeChange = onDoneTypeChange,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun WorkoutStageBox(
    workout: WorkoutEntity,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val fontSize by remember { mutableStateOf(14.sp) }
    val verticalRowPadding by remember { mutableStateOf(8.dp) }

    val color = when (workout.doneType) {
        WorkoutDoneType.NOT_DONE -> Color.Transparent
        WorkoutDoneType.DONE -> Color.Green.copy(alpha = 0.5f)
        WorkoutDoneType.REPEAT -> Color.Blue.copy(alpha = 0.2f)
    }
    Row(modifier = modifier.background(color = color)) {
        Text(
            text = "${workout.stage}",
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(0.4f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(3f)
        ) {
            workout.reps.forEach { rep ->
                Text(
                    text = "$rep",
                    fontSize = fontSize
                )
            }
        }
        Text(
            text = "${workout.repsCount}",
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(0.6f)
        )
        Text(
            text = workout.doneType.title,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.1f))
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .clickable {
                    onDoneTypeChange(workout.copy(doneType = changeDoneType(workout.doneType)))
                }
                .padding(vertical = verticalRowPadding)
                .weight(1.2f)
        )
    }
}

private fun changeDoneType(instant: WorkoutDoneType): WorkoutDoneType {
    return when (instant) {
        WorkoutDoneType.NOT_DONE -> WorkoutDoneType.DONE
        WorkoutDoneType.DONE -> WorkoutDoneType.REPEAT
        WorkoutDoneType.REPEAT -> WorkoutDoneType.NOT_DONE
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
            onDoneTypeChange = {}
        )
    }
}