package com.emikhalets.fitness.presentation.program

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.core.common.ApplicationEntity.Fitness.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.common.toIntOrNull
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.fitness.domain.R
import com.emikhalets.fitness.domain.entity.ExerciseEntity
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.enums.ProgramType
import com.emikhalets.fitness.presentation.program.ProgramContract.Action

private const val TAG = "Program"

@Composable
fun ProgramScreen(
    navigateBack: () -> Unit,
    viewModel: ProgramViewModel,
    programId: Long,
) {
    logi(TAG, "Invoke: id = $programId")
    val state by viewModel.state.collectAsState()

    var deleteProgram by remember { mutableStateOf<ProgramEntity?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetProgram(programId))
    }

    ScreenContent(
        program = state.program,
        onProgramDeleteClick = { deleteProgram = it },
        onBackClick = navigateBack
    )

    val deleteEntity = deleteProgram
    if (deleteEntity != null) {
        logi(TAG, "Show delete dialog: entity = $deleteEntity")
        AppDialogDelete(
            entity = deleteEntity,
            onDeleteClick = { viewModel.setAction(Action.DeleteProgram(it)) }
        )
    }

    if (state.error != null) {
        logi(TAG, "Show error dialog")
        AppDialogMessage(state.error, { viewModel.setAction(Action.DropError) })
    }

    if (state.isProgramDeleted) {
        logi(TAG, "Program deleted")
        AppToast(R.string.app_fitness_program_deleted)
        navigateBack()
    }
}

@Composable
private fun ScreenContent(
    program: ProgramEntity?,
    onProgramDeleteClick: (ProgramEntity?) -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: program = $program")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        program?.let {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Text(
                        text = program.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (program.workouts.isNotEmpty()) {
                    items(program.workouts, key = { it.id }) { workout ->
                        WorkoutBox(
                            workout = workout,
                            onRemoveClick = {},
                            onAddExerciseClick = {},
                            onDeleteExerciseClick = {},
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutBox(
    workout: WorkoutEntity,
    onRemoveClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onDeleteExerciseClick: (ExerciseEntity) -> Unit,
) {
    logi("${TAG}.WorkoutBox", "Invoke: workout = $workout")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = workout.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )
        workout.exercises.forEach { exercise ->
            ExerciseBox(
                exercise = exercise,
                onRepsChanged = {}
            )
        }
    }
}

@Composable
private fun ExerciseBox(
    exercise: ExerciseEntity,
    onRepsChanged: (List<Int>) -> Unit,
) {
    logi("${TAG}.ExerciseBox", "Invoke")
    var newRep by remember { mutableStateOf("") }
    val reps = remember { exercise.reps.toMutableStateList() }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            reps.forEachIndexed { index, rep ->
                AppTextField(
                    value = reps[index].toString(),
                    onValueChange = {
                        val value = it.toIntOrNull()
                        if (value != null) {
                            reps[index] = value
                        } else {
                            reps.removeAt(index)
                        }
                    },
//                    onDoneClick = { onRepsChanged() },
                    onBackspaceEvent = {
                        if (rep == 0) {
                        }
                    }
                )
            }
            AppTextField(
                value = newRep,
                onValueChange = { newRep = it },
                onDoneClick = {
                    reps.add(newRep.toIntOrNull() ?: 0)
                    newRep = ""
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            program = ProgramEntity("", emptyList(), ProgramType.Dynamic),
            onProgramDeleteClick = {},
            onBackClick = {}
        )
    }
}
