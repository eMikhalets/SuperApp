package com.emikhalets.fitness.presentation.program_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationEntity.Fitness.appNameRes
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
    var changedExercise by remember { mutableStateOf<ExerciseEntity?>(null) }

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
                if (exercise != null) {
                    changedExercise = exercise
                } else {
                    newExercises.add(ExerciseEntity())
                    workouts[id] = entity.copy(exercises = newExercises)
                }
            }
        },
        onWorkoutNameChanged = { workout, newName ->
            val id = workouts.indexOf(workout)
            if (id >= 0) workouts[id] = workout.copy(name = newName)
        },
        onBackClick = navigateBack
    )

    if (state.error != null) {
        logi(TAG, "Show error dialog")
        AppDialogMessage(state.error, { viewModel.setAction(Action.DropError) })
    }

    val exercise = changedExercise
    if (exercise != null) {
        logi(TAG, "Show exercise dialog")
        ExerciseDialog(
            exercise = exercise,
            onDoneClick = {
                changedExercise = null
                // TODO: implement exercise name changing
//                val id = workouts.indexOf(workout)
//                workouts[id] = entity.copy(exercises = entity.exercises.toMutableList())
            }
        )
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
    onWorkoutNameChanged: (WorkoutEntity, String) -> Unit,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            if (workouts.isNotEmpty()) {
                items(workouts, key = { it.id }) { workout ->
                    WorkoutBox(
                        workout = workout,
                        onNameChanged = onWorkoutNameChanged,
                        onRemoveClick = { onWorkoutsChangeClick(workout) },
                        onAddExerciseClick = { onExercisesChangeClick(workout, null) },
                        onDeleteExerciseClick = { onExercisesChangeClick(workout, it) },
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
private fun AddWorkoutBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    logi("$TAG.AddWorkoutBox", "Invoke")
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
            Text(
                text = stringResource(R.string.app_fitness_workout_add),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun WorkoutBox(
    workout: WorkoutEntity,
    onNameChanged: (WorkoutEntity, String) -> Unit,
    onRemoveClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onDeleteExerciseClick: (ExerciseEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.WorkoutBox", "Invoke: workout = $workout")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            AppTextField(
                value = workout.name,
                onValueChange = { onNameChanged(workout, it) },
                placeholder = stringResource(R.string.app_fitness_workout_name),
                modifier = Modifier.fillMaxWidth()
            )
            if (workout.exercises.isNotEmpty()) {
                workout.exercises.forEachIndexed { index, exercise ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 4.dp)
                    ) {
                        Text(
                            text = "${index + 1}. ${exercise.name}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                                .clickable { onDeleteExerciseClick(exercise) }
                        )
                    }
                }
            }
            AddExerciseBox(onAddExerciseClick)
        }
    }
}

@Composable
private fun AddExerciseBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.AddExerciseBox", "Invoke")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.app_fitness_exercise_add),
            style = MaterialTheme.typography.h6
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
            workouts = getProgramEditPreview(),
            onWorkoutsChangeClick = {},
            onExercisesChangeClick = { _, _ -> },
            onWorkoutNameChanged = { _, _ -> },
            onBackClick = {}
        )
    }
}
