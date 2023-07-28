package com.emikhalets.feature.workout.presentation.program_add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.feature.workout.R
import com.emikhalets.feature.workout.domain.model.ProgramType
import com.emikhalets.feature.workout.domain.model.WorkoutModel
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Action
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Effect
import com.emikhalets.fitness.domain.entity.ExerciseEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProgramAddScreen(
    navigateBack: () -> Unit,
    viewModel: ProgramAddViewModel,
    programId: Long,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onActionSent = viewModel::setAction,
        onBackClick = navigateBack
    )

    CollectingEffect(
        flow = viewModel.effect,
        navigateBack = navigateBack
    )

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
    }
}

@Composable
private fun CollectingEffect(
    flow: Flow<Effect>,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        flow.collectLatest { effect ->
            when (effect) {
                Effect.ProgramAdded -> navigateBack()
            }
        }
    }
}

@Composable
private fun ScreenContent(
    state: ProgramAddContract.State,
    onActionSent: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    AppContent(R.string.feature_workout, onBackClick) {
        WorkoutsList(
            name = state.name,
            type = state.type,
            workouts = state.workouts,
            onNameChanged = { onActionSent(Action.NameChanged(it)) },
            onAddWorkoutClick = { onActionSent(Action.AddWorkout) }
        )
    }

    AppErrorDialog(message = state.error)
}

@Composable
private fun WorkoutsList(
    name: String,
    type: ProgramType,
    workouts: List<WorkoutModel>,
    onNameChanged: (String) -> Unit,
    onAddWorkoutClick: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AppTextField(
                value = name,
                onValueChange = onNameChanged,
                placeholder = stringResource(R.string.feature_workout_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
//        item {
        // type chooser
//            AddProgramBox(
//                onClick = onAddProgramClick,
//                modifier = Modifier.padding(8.dp, 4.dp)
//            )
//        }
        items(workouts, key = { it.name }) { item ->
            WorkoutBox(
                workout = item,
                onNameChanged = onWorkoutNameChanged,
                onRemoveClick = { onWorkoutsChangeClick(item) },
                onAddExerciseClick = { onExercisesChangeClick(item, null) },
                onDeleteExerciseClick = { onExercisesChangeClick(item, it) },
            )
        }
        item {
            AddWorkoutBox(onAddWorkoutClick)
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
