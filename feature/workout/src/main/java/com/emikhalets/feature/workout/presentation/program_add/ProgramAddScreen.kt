package com.emikhalets.feature.workout.presentation.program_add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppCardColumn
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
            onAddWorkoutClick = { onActionSent(Action.AddWorkout) },
            onRemoveWorkoutClick = { onActionSent(Action.RemoveWorkout(it)) },
            onWorkoutClick = { onActionSent(Action.SetWorkoutEdit(it)) },
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
    onRemoveWorkoutClick: (WorkoutModel) -> Unit,
    onWorkoutClick: (WorkoutModel) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AppTextField(
                value = name,
                onValueChange = onNameChanged,
                padding = PaddingValues(12.dp),
                placeholder = stringResource(R.string.feature_workout_name),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            AppSpinner(
                items = ProgramType.getList(),
                selected = type,
                padding = PaddingValues(12.dp),
                onSelect = { onTypeChanged(it) },
                nameGetter = { it.getName() }
            )
        }
        itemsIndexed(workouts, key = { _, item -> item.name }) { index, item ->
            WorkoutBox(
                model = item,
                onRemoveWorkoutClick = onRemoveWorkoutClick,
                onWorkoutClick = onWorkoutClick
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
    onRemoveWorkoutClick: (WorkoutModel) -> Unit,
    onWorkoutClick: (WorkoutModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCardColumn(
        innerPadding = PaddingValues(12.dp),
        onClick = { onWorkoutClick(model) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTextField(
                value = model.name,
                onValueChange = {},
                placeholder = stringResource(R.string.feature_workout_name),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier.clickable { onRemoveWorkoutClick(model) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        model.exercises.forEachIndexed { index, exercise ->
            Text(
                text = "${index + 1}. ${exercise.name}",
                style = MaterialTheme.typography.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
                    .padding(8.dp, 4.dp)
            )
        }
        if (model.exercises.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
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
                    .padding(start = 24.dp)
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
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
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
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
