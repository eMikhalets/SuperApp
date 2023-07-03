package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.ApplicationItem
import com.emikhalets.core.common.ApplicationItem.Notes.appNameRes
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppFloatButton
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Action
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Effect

private const val TAG = "TasksList"

@Composable
fun TasksListScreen(
    navigateBack: () -> Unit,
    viewModel: TasksListViewModel,
) {
    logi(TAG, "Invoke")
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
        onSubtaskComplete = { entity, checked ->
            viewModel.setAction(Action.CompleteSubtask(entity, checked))
        },
        onAddTaskClick = { taskEntity = TaskEntity("") },
        onCollapseTasksClick = { checkedTasksCollapsed = !checkedTasksCollapsed },
        onBackClick = navigateBack
    )

    when (effect) {
        is Effect.Error -> {
            logi(TAG, "Set effect: error")
            AppDialogMessage((effect as Effect.Error).message)
        }

        is Effect.DeleteTaskDialog -> {
            logi(TAG, "Set effect: delete task")
            AppDialogDelete(
                entity = (effect as Effect.DeleteTaskDialog).entity,
                onDeleteClick = { viewModel.setAction(Action.DeleteTask(it)) }
            )
        }

        null -> Unit
    }

    val taskValue = taskEntity
    if (taskValue != null) {
        logi(TAG, "Show task dialog: entity = $taskValue")
        TaskDialog(
            task = taskValue,
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
    onSubtaskComplete: (SubtaskEntity, Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCollapseTasksClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi(
        "$TAG.ScreenContent", "Invoke:\n" +
                "tasks = $tasksList,\n" +
                "completed = $checkedTasksList,\n" +
                "completedCollapsed = $checkedTasksCollapsed"
    )
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tasksList) { entity ->
                        TaskBox(
                            entity = entity,
                            onCheckTask = onTaskComplete,
                            onCheckSubtask = onSubtaskComplete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 4.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { onTaskClick(entity) }
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
                                    onCheckSubtask = onSubtaskComplete,
                                    modifier = Modifier
                                        .fillMaxWidth()
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
    logi(
        "$TAG.CompletedTasksHeader", "Invoke:\n" +
                "tasksSize = $tasksSize,\n" +
                "checkedTasksCollapsed = $checkedTasksCollapsed"
    )
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
    onCheckSubtask: (SubtaskEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.TaskBox", "Invoke: entity = $entity")
    // TODO: temp keep subtasks expanded value in composable
    var expanded by remember { mutableStateOf(true) }

    Card(elevation = 0.dp, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskRow(
                    text = entity.content,
                    checked = entity.isCompleted,
                    onChecked = { onCheckTask(entity, it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                if (entity.subtasks.isNotEmpty()) {
                    SubtasksCountBox(
                        count = entity.subtasksCount,
                        completed = entity.subtasksCompletedCount,
                        expanded = expanded,
                        onClick = { expanded = !expanded }
                    )
                }
            }
            if (!entity.isCompleted && entity.subtasks.isNotEmpty() && expanded) {
                entity.subtasks.forEach { subtask ->
                    TaskRow(
                        text = subtask.content,
                        checked = subtask.isCompleted,
                        onChecked = { onCheckSubtask(subtask, it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskRow(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    bold: Boolean = false,
) {
    logi("$TAG.TaskRow", "Invoke: text = $text, checked = $checked")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChecked,
            modifier = Modifier.align(Alignment.Top)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = if (checked) Color.Gray else Color.Unspecified,
            textDecoration = if (checked) TextDecoration.LineThrough else null,
            fontWeight = if (bold) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SubtasksCountBox(
    count: Int,
    completed: Int,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi(
        "$TAG.SubtasksCountBox", "Invoke:\n" +
                "count = $count,\n" +
                "completed = $completed,\n" +
                "expanded = $expanded"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp, 2.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = stringResource(R.string.app_notes_counter, completed, count),
            style = MaterialTheme.typography.body1,
        )
        Icon(
            imageVector = if (expanded) {
                Icons.Rounded.KeyboardArrowDown
            } else {
                Icons.Rounded.KeyboardArrowUp
            },
            contentDescription = null,
            tint = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier
                .padding(start = 12.dp)
                .background(MaterialTheme.colors.secondary, CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            tasksList = getTasksListPreview(false),
            checkedTasksList = getTasksListPreview(true),
            checkedTasksCollapsed = false,
            onTaskClick = {},
            onTaskComplete = { _, _ -> },
            onSubtaskComplete = { _, _ -> },
            onAddTaskClick = {},
            onCollapseTasksClick = {},
            onBackClick = {}
        )
    }
}
