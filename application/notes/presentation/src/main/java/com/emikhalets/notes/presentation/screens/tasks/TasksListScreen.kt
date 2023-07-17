package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppFloatButtonBox
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import com.emikhalets.notes.presentation.screens.tasks.TasksListContract.Action

private const val TAG = "TasksList"

@Composable
fun TasksListScreen(
    navigateBack: () -> Unit,
    viewModel: TasksListViewModel,
) {
    logi(TAG, "Invoke")
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetTask)
    }

    ScreenContent(
        state = state,
        onTaskClick = { viewModel.setAction(Action.SetEditingTask(it)) },
        onTaskComplete = { entity, checked ->
            viewModel.setAction(Action.CheckTask(entity, checked))
        },
        onSubtaskComplete = { entity, checked ->
            viewModel.setAction(Action.CheckSubtask(entity, checked))
        },
        onAddTaskClick = { viewModel.setAction(Action.SetEditingTask()) },
        onCollapseTasksClick = { viewModel.setAction(Action.SwitchCheckedExpand) },
        onBackClick = navigateBack
    )

    TaskDialog(
        task = state.editingTask,
        onDoneClick = { task -> viewModel.setAction(Action.CheckTask(task, false)) }
    )

    AppErrorDialog(
        message = state.error,
        onDismiss = { viewModel.setAction(Action.DropError) }
    )

    if (state.isTaskDeleted) {
        logi(TAG, "Task deleted")
        AppToast(R.string.app_notes_task_deleted)
        navigateBack()
    }
}

@Composable
private fun ScreenContent(
    state: TasksListContract.State,
    onTaskClick: (TaskEntity) -> Unit,
    onTaskComplete: (TaskEntity, Boolean) -> Unit,
    onSubtaskComplete: (SubtaskEntity, Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCollapseTasksClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: state = $state")

    AppContent(ApplicationEntity.Notes.getName(), onBackClick) {
        AppFloatButtonBox(Icons.Rounded.Add, { onAddTaskClick() }) {
            TasksList(
                tasksList = state.tasksList,
                checkedTasksList = state.checkedList,
                isCheckedTasksExpanded = state.isCheckedTasksExpanded,
                onTaskClick = onTaskClick,
                onTaskComplete = onTaskComplete,
                onSubtaskComplete = onSubtaskComplete,
                onAddTaskClick = onAddTaskClick,
                onCollapseTasksClick = onCollapseTasksClick,
            )
        }
    }
}

@Composable
private fun TasksList(
    tasksList: List<TaskEntity>,
    checkedTasksList: List<TaskEntity>,
    isCheckedTasksExpanded: Boolean,
    onTaskClick: (TaskEntity) -> Unit,
    onTaskComplete: (TaskEntity, Boolean) -> Unit,
    onSubtaskComplete: (SubtaskEntity, Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCollapseTasksClick: () -> Unit,
) {
    logi(
        "$TAG.TasksList", "Invoke:\n" +
                "tasks = $tasksList,\n" +
                "completed = $checkedTasksList,\n" +
                "isCheckedTasksExpanded = $isCheckedTasksExpanded"
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tasksList, { it.id }) { entity ->
            TaskBox(
                entity = entity,
                onCheckTask = onTaskComplete,
                onCheckSubtask = onSubtaskComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp)
                    .clickable { onTaskClick(entity) }
            )
        }
        if (checkedTasksList.isNotEmpty()) {
            item {
                CompletedTasksHeader(
                    tasksSize = checkedTasksList.size,
                    isCheckedTasksExpanded = isCheckedTasksExpanded,
                    onCollapseTasksClick = onCollapseTasksClick
                )
            }
            if (isCheckedTasksExpanded) {
                items(checkedTasksList, { it.id }) { entity ->
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

@Composable
private fun CompletedTasksHeader(
    tasksSize: Int,
    isCheckedTasksExpanded: Boolean,
    onCollapseTasksClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi(
        "$TAG.CompletedTasksHeader", "Invoke:\n" +
                "tasksSize = $tasksSize,\n" +
                "isCheckedTasksExpanded = $isCheckedTasksExpanded"
    )
    val collapsedTasksIcon = if (isCheckedTasksExpanded) {
        Icons.Default.KeyboardArrowDown
    } else {
        Icons.Default.KeyboardArrowUp
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCollapseTasksClick() }
            .padding(start = 28.dp)
    ) {
        Text(
            text = stringResource(R.string.app_notes_completed, tasksSize),
            style = MaterialTheme.typography.text,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = collapsedTasksIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
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

    AppCard(modifier = modifier.padding(8.dp, 4.dp)) {
        TaskRow(
            entity = entity,
            expanded = expanded,
            onExpandSwitch = { expanded = !expanded },
            onChecked = { onCheckTask(entity, it) }
        )
        SubtasksBox(
            subtasks = entity.subtasks,
            isExpanded = expanded,
            isCompleted = entity.isCompleted,
            onChecked = { subtask, checked -> onCheckSubtask(subtask, checked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp)
        )
    }
}

@Composable
private fun TaskRow(
    entity: TaskEntity,
    expanded: Boolean,
    onExpandSwitch: () -> Unit,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    bold: Boolean = false,
) {
    logi("$TAG.TaskRow", "Invoke: entity = $entity")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(
            checked = entity.isCompleted,
            onCheckedChange = onChecked
        )
        Spacer(modifier = Modifier.width(8.dp))
        TaskText(
            text = entity.content,
            isCompleted = entity.isCompleted
        )
        if (entity.subtasks.isNotEmpty()) {
            SubtasksCountBox(
                count = entity.subtasksCount,
                completed = entity.subtasksCompletedCount,
                expanded = expanded,
                onClick = onExpandSwitch,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

@Composable
private fun RowScope.TaskText(
    text: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier,
    bold: Boolean = false,
) {
    logi("$TAG.TaskText", "Invoke: text = $text")

    val textColor = if (isCompleted) MaterialTheme.colors.secondary
    else MaterialTheme.colors.onSurface

    val textDecoration = if (isCompleted) TextDecoration.LineThrough else null

    val fontWeight = if (bold) FontWeight.Medium else FontWeight.Normal

    Text(
        text = text,
        style = MaterialTheme.typography.text,
        color = textColor,
        textDecoration = textDecoration,
        fontWeight = fontWeight,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    )
}

@Composable
private fun SubtasksBox(
    subtasks: List<SubtaskEntity>,
    isExpanded: Boolean,
    isCompleted: Boolean,
    onChecked: (SubtaskEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.SubtasksBox", "Invoke: subtasks = $subtasks")

    if (!isCompleted && subtasks.isNotEmpty() && isExpanded) {
        subtasks.forEach { subtask ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Checkbox(
                    checked = subtask.isCompleted,
                    onCheckedChange = { onChecked(subtask, it) }
                )
                TaskText(
                    text = subtask.content,
                    isCompleted = subtask.isCompleted
                )
            }
        }
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
            style = MaterialTheme.typography.text,
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
            state = TasksListContract.State(
                tasksList = getTasksListPreview(),
                checkedList = getCheckedTasksListPreview(),
                isCheckedTasksExpanded = true
            ),
            onTaskClick = {},
            onTaskComplete = { _, _ -> },
            onSubtaskComplete = { _, _ -> },
            onAddTaskClick = {},
            onCollapseTasksClick = {},
            onBackClick = {}
        )
    }
}
