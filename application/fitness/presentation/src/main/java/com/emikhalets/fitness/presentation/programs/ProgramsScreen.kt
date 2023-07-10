package com.emikhalets.fitness.presentation.programs

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.common.ApplicationEntity.Fitness.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.fitness.domain.R
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.presentation.programs.ProgramsContract.Action

private const val TAG = "Programs"

@Composable
fun ProgramsScreen(
    navigateToProgram: (id: Long) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProgramsViewModel,
) {
    logi(TAG, "Invoke")
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetPrograms)
    }

    ScreenContent(
        programs = state.programs,
        onProgramClick = { entity -> navigateToProgram(entity.id) },
        onAddProgramClick = { navigateToProgram(AppCode.IdNull) },
        onBackClick = navigateBack
    )

    if (state.error != null) {
        logi(TAG, "Show error dialog")
        AppDialogMessage(state.error, { viewModel.setAction(Action.DropError) })
    }
}

@Composable
private fun ScreenContent(
    programs: List<ProgramEntity>,
    onProgramClick: (ProgramEntity) -> Unit,
    onAddProgramClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: programs = $programs")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(programs, key = { it.id }) { entity ->
                ProgramBox(
                    entity = entity,
                    onProgramClick = onProgramClick
                )
            }
            item {
                AddProgramBox(
                    onClick = onAddProgramClick,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ProgramBox(
    entity: ProgramEntity,
    onProgramClick: (ProgramEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.ProgramBox", "Invoke: entity = $entity")
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
            programs = getProgramsPreview(),
            onProgramClick = {},
            onAddProgramClick = {},
            onBackClick = {}
        )
    }
}
