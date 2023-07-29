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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppSpinner
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.feature.workout.R
import com.emikhalets.feature.workout.domain.model.ExerciseModel
import com.emikhalets.feature.workout.domain.model.ProgramType
import com.emikhalets.feature.workout.domain.model.WorkoutModel
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Action
import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Effect
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
            onTypeChanged = { onActionSent(Action.TypeChanged(it)) },
            onAddWorkoutClick = { onActionSent(Action.ChangeWorkouts()) },
            onRemoveWorkoutClick = { onActionSent(Action.ChangeWorkouts(it)) },
            onAddExerciseClick = { onActionSent(Action.ChangeExercises(it)) },
            onRemoveExerciseClick = { p, i -> onActionSent(Action.ChangeExercises(p, i)) },
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
    onTypeChanged: (ProgramType) -> Unit,
    onAddWorkoutClick: () -> Unit,
    onRemoveWorkoutClick: (Int) -> Unit,
    onAddExerciseClick: (Int) -> Unit,
    onRemoveExerciseClick: (Int, Int) -> Unit,
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
        item {
            AppSpinner(
                items = ProgramType.getList(),
                selected = type,
                onSelect = { onTypeChanged(it) },
                nameGetter = { it.getName() },
                modifier = Modifier.padding(8.dp, 4.dp)
            )
        }
        itemsIndexed(workouts, key = { _, item -> item.name }) { index, item ->
            WorkoutBox(
                model = item,
                onRemoveWorkoutClick = { onRemoveWorkoutClick(index) },
                onAddExerciseClick = { onAddExerciseClick(index) },
                onRemoveExerciseClick = { onRemoveExerciseClick(index, it) },
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
    AppCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
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
                text = stringResource(R.string.feature_workout_add_workout),
                style = MaterialTheme.typography.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun WorkoutBox(
    model: WorkoutModel,
    onRemoveWorkoutClick: (Int) -> Unit,
    onAddExerciseClick: () -> Unit,
    onRemoveExerciseClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            AppTextField(
                value = model.name,
                onValueChange = {},
                placeholder = stringResource(R.string.feature_workout_name),
                modifier = Modifier.fillMaxWidth()
            )
            ExercisesList(
                list = model.exercises,
                onDeleteExerciseClick = onRemoveExerciseClick,
            )
            AddExerciseBox(onAddExerciseClick)
        }
    }
}

@Composable
private fun ExercisesList(
    list: List<ExerciseModel>,
    onDeleteExerciseClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        list.forEachIndexed { index, exercise ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp)
            ) {
                Text(
                    text = "${index + 1}. ${exercise.name}",
                    style = MaterialTheme.typography.text,
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
                        .clickable { onDeleteExerciseClick(index) }
                )
            }
        }
    }
}

@Composable
private fun AddExerciseBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            text = stringResource(R.string.feature_workout_add_exercise),
            style = MaterialTheme.typography.text
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = ProgramAddContract.State(
                name = "Program #1",
                workouts = getProgramAddPreview(),
            ),
            onActionSent = {},
            onBackClick = {}
        )
    }
}
