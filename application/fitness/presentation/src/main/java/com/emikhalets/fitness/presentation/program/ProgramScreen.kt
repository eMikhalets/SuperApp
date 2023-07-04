package com.emikhalets.fitness.presentation.program

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationItem.Fitness.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.fitness.domain.R
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.presentation.program.ProgramContract.Action
import com.emikhalets.fitness.presentation.program.ProgramContract.Effect

private const val TAG = "Program"

@Composable
fun ProgramScreen(
    navigateBack: () -> Unit,
    viewModel: ProgramViewModel,
    programId: Long,
) {
    logi(TAG, "Invoke: id = $programId")
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    var deleteProgram by remember { mutableStateOf<ProgramEntity?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetProgram(programId))
    }

    ScreenContent(
        program = state.program,
        onProgramDeleteClick = { deleteProgram = it },
        onBackClick = navigateBack
    )

    when (effect) {
        is Effect.Error -> {
            logi(TAG, "Set effect: error")
            AppDialogMessage((effect as Effect.Error).message)
        }

        Effect.ProgramDeleted -> {
            logi(TAG, "Set effect: program deleted")
            AppToast(R.string.app_fitness_program_deleted)
            navigateBack()
        }

        null -> Unit
    }

    val deleteEntity = deleteProgram
    if (deleteEntity != null) {
        logi(TAG, "Show delete dialog: entity = $deleteEntity")
        AppDialogDelete(
            entity = deleteEntity,
            onDeleteClick = { viewModel.setAction(Action.DeleteProgram(it)) }
        )
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
        if (program != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(program.workouts, key = { it.id }) { entity ->
                    WorkoutBox(entity = entity)
                }
            }
        }
    }
}

@Composable
private fun WorkoutBox(entity: WorkoutEntity, modifier: Modifier = Modifier) {
    logi("$TAG.WorkoutBox", "Invoke: entity = $entity")
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onProgramClick(entity) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = entity.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AddProgramBox(onClick: () -> Unit, modifier: Modifier = Modifier) {
    logi("$TAG.AddProgramBox", "Invoke")
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
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
                    .size(24.dp)
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.app_fitness_add_program),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            program = ProgramEntity("", emptyList()),
            onProgramDeleteClick = {},
            onBackClick = {}
        )
    }
}
