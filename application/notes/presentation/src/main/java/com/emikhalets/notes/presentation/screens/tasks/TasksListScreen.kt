package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppDialogDelete
import com.emikhalets.core.ui.component.AppDialogMessage
import com.emikhalets.core.ui.component.AppFloatButton
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Action
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Effect

@Composable
fun TasksListScreen(
    navigateBack: () -> Unit,
    viewModel: TasksListViewModel,
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    var taskEntity by remember { mutableStateOf<TaskEntity?>(null) }
    var checkedTasksCollapsed by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetTask)
    }

    ScreenContent(
        tasksList = state.tasksList,
        checkedTasksList = state.checkedList,
        checkedTasksCollapsed = checkedTasksCollapsed,
        onTaskClick = { entity -> taskEntity = entity },
        onTaskComplete = { entity, checked ->
            viewModel.setAction(Action.CompleteTask(entity, checked))
        },
        onAddTaskClick = { taskEntity = TaskEntity("") },
        onCollapseTasksClick = { checkedTasksCollapsed = !checkedTasksCollapsed },
        onBackClick = navigateBack
    )

    when (effect) {
        is Effect.Error -> AppDialogMessage((effect as Effect.Error).message)

        is Effect.DeleteTaskDialog -> {
            AppDialogDelete(
                entity = (effect as Effect.DeleteTaskDialog).entity,
                onDeleteClick = { viewModel.setAction(Action.DeleteTask(it)) }
            )
        }

        null -> Unit
    }

    if (taskEntity != null) {
        TaskDialog(
            task = taskEntity!!,
            onDismiss = { taskEntity = null },
            onDoneClick = { task ->
                taskEntity = null
                viewModel.setAction(Action.CompleteTask(task, false))
            }
        )
    }
}

@Composable
private fun ScreenContent(
    tasksList: List<TaskEntity>,
    checkedTasksList: List<TaskEntity>,
    checkedTasksCollapsed: Boolean,
    onTaskClick: (TaskEntity) -> Unit,
    onTaskComplete: (TaskEntity, Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCollapseTasksClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppChildScreenBox(onBackClick, stringResource(com.emikhalets.core.R.string.application_notes)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tasksList) { entity ->
                        TaskBox(
                            entity = entity,
                            onCheckTask = onTaskComplete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTaskClick(entity) }
                                .padding(8.dp, 4.dp),
                        )
                    }
                    if (checkedTasksList.isNotEmpty()) {
                        item {
                            CompletedTasksHeader(
                                tasksSize = checkedTasksList.size,
                                checkedTasksCollapsed = checkedTasksCollapsed,
                                onCollapseTasksClick = onCollapseTasksClick
                            )
                        }
                        if (!checkedTasksCollapsed) {
                            items(checkedTasksList) { entity ->
                                TaskBox(
                                    entity = entity,
                                    onCheckTask = onTaskComplete,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onTaskClick(entity) }
                                        .padding(8.dp, 4.dp),
                                )
                            }
                        }
                    }
                }
            }
            AppFloatButton(
                icon = Icons.Rounded.Add,
                onClick = { onAddTaskClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun CompletedTasksHeader(
    tasksSize: Int,
    checkedTasksCollapsed: Boolean,
    onCollapseTasksClick: () -> Unit,
) {
    val collapsedTasksIcon = if (checkedTasksCollapsed) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCollapseTasksClick() }
            .padding(start = 28.dp)
    ) {
        Text(
            text = stringResource(R.string.app_notes_completed, tasksSize),
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = collapsedTasksIcon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun TaskBox(
    entity: TaskEntity,
    onCheckTask: (TaskEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 0.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 2.dp)
            ) {
                Checkbox(
                    checked = entity.isCompleted,
                    onCheckedChange = { onCheckTask(entity, it) }
                )
                Text(
                    text = entity.content,
                    color = if (entity.isCompleted) Color.Gray else Color.Unspecified,
                    textDecoration = if (entity.isCompleted) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            tasksList = getTasksListPreview(),
            checkedTasksList = getTasksListPreview(),
            checkedTasksCollapsed = false,
            onTaskClick = {},
            onTaskComplete = { _, _ -> },
            onAddTaskClick = {},
            onCollapseTasksClick = {},
            onBackClick = {}
        )
    }
}
