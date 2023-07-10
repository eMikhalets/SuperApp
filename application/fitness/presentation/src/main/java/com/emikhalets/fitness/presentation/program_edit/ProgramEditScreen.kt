package com.emikhalets.fitness.presentation.program_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationItem.Fitness.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.fitness.domain.R
import com.emikhalets.fitness.domain.entity.ExerciseEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.presentation.program_edit.ProgramEditContract.Action

private const val TAG = "ProgramEdit"

@Composable
fun ProgramEditScreen(
    navigateBack: () -> Unit,
    viewModel: ProgramEditViewModel,
    programId: Long,
) {
    logi(TAG, "Invoke: programId = $programId")
    val state by viewModel.state.collectAsState()

    var name by remember { mutableStateOf("") }
    val workouts = remember { mutableStateListOf<WorkoutEntity>() }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetProgram(programId))
    }

    LaunchedEffect(state.program) {
        val entity = state.program
        if (entity != null) {
            name = entity.name
        }
    }

    ScreenContent(
        name = name,
        onNameChanged = { name = it },
        workouts = workouts,
        onWorkoutsChangeClick = { workout ->
            workout?.let { workouts.remove(it) }
                ?: workouts.add(WorkoutEntity())
        },
        onExercisesChangeClick = { workout, exercise ->
            val id = workouts.indexOf(workout)
            if (id >= 0) {
                val entity = workouts[id]
                val newExercises = entity.exercises.toMutableList()
                workouts[id] = if (exercise != null) {
                    newExercises.remove(exercise)
                    entity.copy(exercises = newExercises)
                } else {
                    newExercises.add(ExerciseEntity())
                    entity.copy(exercises = newExercises)
                }
            }
        },
        onBackClick = navigateBack
    )

    if (state.error != null) {
        logi(TAG, "Show error dialog")
        AppDialogMessage(state.error, { viewModel.setAction(Action.DropError) })
    }

    if (state.isProgramSaved) {
        logi(TAG, "Program saved")
        AppToast(R.string.app_fitness_program_saved)
        navigateBack()
    }
}

@Composable
private fun ScreenContent(
    name: String,
    onNameChanged: (String) -> Unit,
    workouts: List<WorkoutEntity>,
    onWorkoutsChangeClick: (WorkoutEntity?) -> Unit,
    onExercisesChangeClick: (WorkoutEntity, ExerciseEntity?) -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: name = $name")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                AppTextField(
                    value = name,
                    onValueChange = onNameChanged,
                    placeholder = stringResource(R.string.app_fitness_program_name),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (workouts.isNotEmpty()) {
                items(workouts, key = { it.id }) { workout ->
                    WorkoutBox(
                        name = workout.name,
                        onNameChanged = {},
                        exercises = workout.exercises,
                        onRemoveClick = { onWorkoutsChangeClick(workout) },
                        onAddExerciseClick = {},
                        onDeleteExerciseClick = {},
                    )
                }
            }
            item {
                AddWorkoutBox(
                    onClick = { onWorkoutsChangeClick(null) }
                )
            }
        }
    }
}

@Composable
private fun AddWorkoutBox(onClick: () -> Unit) {
    logi("$TAG.AddWorkoutBox", "Invoke")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(R.string.app_fitness_workout_add),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WorkoutBox(
    name: String,
    onNameChanged: (String) -> Unit,
    exercises: List<ExerciseEntity>,
    onRemoveClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onDeleteExerciseClick: (ExerciseEntity) -> Unit,
) {
    logi("$TAG.WorkoutBox", "Invoke")
    Column(modifier = Modifier.fillMaxWidth()) {
        AppTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = stringResource(R.string.app_fitness_workout_name),
            modifier = Modifier.fillMaxWidth()
        )
        if (exercises.isNotEmpty()) {
            exercises.forEachIndexed { index, exercise ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${index + 1}. ${exercise.name}",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(8.dp)
                            .clickable { onDeleteExerciseClick(exercise) }
                    )
                }
            }
        }
        AddExerciseBox(onAddExerciseClick)
    }
}

@Composable
private fun AddExerciseBox(onClick: () -> Unit) {
    logi("$TAG.AddExerciseBox", "Invoke")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(R.string.app_fitness_exercise_add),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            name = "Program #1",
            onNameChanged = {},
            workouts = listOf(
                WorkoutEntity(
                    0, "Workout #1",
                    listOf(
                        ExerciseEntity(0, "Exercise #1"),
                        ExerciseEntity(0, "Exercise #2"),
                        ExerciseEntity(0, "Exercise #3"),
                        ExerciseEntity(0, "Exercise #4"),
                        ExerciseEntity(0, "Exercise #5")
                    )
                ),
                WorkoutEntity(
                    0, "Workout #2",
                    listOf(
                        ExerciseEntity(0, "Exercise #1"),
                        ExerciseEntity(0, "Exercise #2"),
                        ExerciseEntity(0, "Exercise #3"),
                    )
                ),
                WorkoutEntity(
                    0, "Workout #3",
                    listOf(
                        ExerciseEntity(0, "Exercise #1"),
                        ExerciseEntity(0, "Exercise #2"),
                        ExerciseEntity(0, "Exercise #3"),
                        ExerciseEntity(0, "Exercise #4"),
                    )
                ),
            ),
            onWorkoutsChangeClick = {},
            onExercisesChangeClick = { _, _ -> },
            onBackClick = {}
        )
    }
}
