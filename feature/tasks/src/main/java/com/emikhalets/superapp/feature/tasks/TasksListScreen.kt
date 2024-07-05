package com.emikhalets.superapp.feature.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.superapp.core.ui.component.AppFloatingButtonBox
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.domain.tasks.TaskModel
import com.emikhalets.superapp.feature.tasks.TasksContract.Action
import com.emikhalets.superapp.feature.tasks.TasksContract.State

@Composable
internal fun TasksListScreen(
    navigateBack: () -> Unit,
    viewModel: TasksViewModel,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onSetAction = viewModel::setAction,
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    state: State,
    onSetAction: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppFloatingButtonBox(onClick = { onSetAction(Action.SetEditTask()) }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                items(state.tasksList) { task ->
                    TaskItemBox(
                        task = task,
                        onClick = { onSetAction(Action.SetEditTask(it)) },
                        onCheckTask = { onSetAction(Action.CheckTask(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    )
                }
            }
        }
        TaskEditDialog(
            task = state.editTask,
            onSaveClick = { onSetAction(Action.SaveEditTask(it)) }
        )
    }
}

@Composable
private fun TaskItemBox(
    task: TaskModel,
    onClick: (TaskModel) -> Unit,
    onCheckTask: (TaskModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .clickableOnce { onClick(task) }
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TaskItemRow(
                task = task,
                parentCompleted = false,
                onChecked = onCheckTask
            )
            task.subtasks.forEach { subtask ->
                TaskItemRow(
                    task = subtask,
                    parentCompleted = task.completed,
                    onChecked = onCheckTask,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }
    }
}

@Composable
private fun TaskItemRow(
    task: TaskModel,
    parentCompleted: Boolean,
    onChecked: (TaskModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val completed = if (parentCompleted) true else task.completed
    val textDecoration = if (completed) TextDecoration.LineThrough else null
    val textColor = if (completed) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onBackground
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Checkbox(
            checked = completed,
            enabled = !completed,
            onCheckedChange = { onChecked(task) },
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = task.content,
            color = textColor,
            fontSize = 16.sp,
            textDecoration = textDecoration,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                tasksList = getTasksListPreview(),
                editTask = null,
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}