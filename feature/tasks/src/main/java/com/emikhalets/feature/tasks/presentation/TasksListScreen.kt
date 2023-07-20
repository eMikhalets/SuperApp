package com.emikhalets.feature.tasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppFloatButtonBox
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.setExpandedIcon
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.feature.tasks.R
import com.emikhalets.feature.tasks.data.TaskModel
import com.emikhalets.feature.tasks.presentation.TasksListContract.Action

private const val TAG = "TasksList"

@Composable
fun TasksListScreen(
    navigateBack: () -> Unit,
    viewModel: TasksListViewModel,
) {
    logi(TAG, "Invoke")
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onActionSent = viewModel::setAction,
        onBackClick = navigateBack
    )

    if (state.showTaskDialog) {
        TaskEditDialog(
            task = state.editingTask,
            onDoneClick = { task -> viewModel.setAction(Action.SaveTask(task)) },
            onDismiss = { viewModel.setAction(Action.DropTaskDialog) }
        )
    }

    AppErrorDialog(
        message = state.error,
        onDismiss = { viewModel.setAction(Action.DropError) }
    )
}

@Composable
private fun ScreenContent(
    state: TasksListContract.State,
    onActionSent: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: state = $state")

    AppContent(ApplicationEntity.Notes.getName(), onBackClick) {
        AppFloatButtonBox(Icons.Rounded.Add, { onActionSent(Action.TaskClicked()) }) {
            TasksList(
                tasksList = state.tasksList,
                checkedTasksList = state.checkedList,
                isCheckedTasksExpanded = state.isCheckedTasksExpanded,
                onTaskClick = { onActionSent(Action.TaskClicked(it)) },
                onTaskComplete = { model, value -> onActionSent(Action.CheckTask(model, value)) },
                onAddTaskClick = { onActionSent(Action.TaskClicked()) },
                onCollapseTasksClick = { onActionSent(Action.SwitchCheckedExpand) },
            )
        }
    }
}

@Composable
private fun TasksList(
    tasksList: List<TaskModel>,
    checkedTasksList: List<TaskModel>,
    isCheckedTasksExpanded: Boolean,
    onTaskClick: (TaskModel) -> Unit,
    onTaskComplete: (TaskModel, Boolean) -> Unit,
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
        items(tasksList, { it.id }) { item ->
            TaskBox(
                model = item,
                onCheckTask = onTaskComplete,
                onTaskClick = onTaskClick
            )
        }
        if (checkedTasksList.isNotEmpty()) {
            item {
                CompletedTasksRow(
                    tasksSize = checkedTasksList.size,
                    expanded = isCheckedTasksExpanded,
                    onExpandClick = onCollapseTasksClick
                )
            }
            if (isCheckedTasksExpanded) {
                items(checkedTasksList, { it.id }) { item ->
                    TaskBox(
                        model = item,
                        onCheckTask = onTaskComplete,
                        onTaskClick = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CompletedTasksRow(
    tasksSize: Int,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.CompletedTasksRow", "Invoke: tasksSize = $tasksSize, expanded = $expanded")

    val collapsedTasksIcon = if (expanded) {
        Icons.Default.KeyboardArrowDown
    } else {
        Icons.Default.KeyboardArrowUp
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onExpandClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.feature_tasks_completed, tasksSize),
            style = MaterialTheme.typography.text,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.width(32.dp))
        Icon(
            imageVector = collapsedTasksIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun TaskBox(
    model: TaskModel,
    onCheckTask: (TaskModel, Boolean) -> Unit,
    onTaskClick: ((TaskModel) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.TaskBox", "Invoke: model = $model")

    // TODO: temp keep subtasks expanded value in composable
    var expanded by remember { mutableStateOf(true) }

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
            .clickable(enabled = onTaskClick != null) { onTaskClick?.invoke(model) }
    ) {
        ParentTaskRow(
            model = model,
            hasSubtasks = model.subtasks.isNotEmpty(),
            expanded = expanded,
            onSubtasksExpanded = { expanded = !expanded },
            onTaskChecked = onCheckTask
        )
        SubtasksBox(
            subtasks = model.subtasks,
            isExpanded = expanded,
            isCompleted = model.completed,
            onChecked = { subtask, checked -> onCheckTask(subtask, checked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp)
        )
    }
}

@Composable
private fun ParentTaskRow(
    model: TaskModel,
    hasSubtasks: Boolean,
    expanded: Boolean,
    onSubtasksExpanded: () -> Unit,
    onTaskChecked: (TaskModel, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    bold: Boolean = false,
) {
    logi("$TAG.TaskRow", "Invoke: model = $model")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        SimpleTaskRow(
            text = model.content,
            checked = model.completed,
            onCheckedChange = { onTaskChecked(model, it) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (hasSubtasks) {
            SubtasksCountBox(
                count = model.subtasksCount,
                completed = model.subtasksCompletedCount,
                expanded = expanded,
                onClick = onSubtasksExpanded,
                modifier = Modifier.align(Alignment.Top)
            )
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
            text = stringResource(R.string.feature_tasks_counter, completed, count),
            style = MaterialTheme.typography.text,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            imageVector = setExpandedIcon(expanded),
            contentDescription = null,
            tint = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.background(MaterialTheme.colors.secondary, CircleShape)
        )
    }
}

@Composable
private fun SubtasksBox(
    subtasks: List<TaskModel>,
    isExpanded: Boolean,
    isCompleted: Boolean,
    onChecked: (TaskModel, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.SubtasksBox", "Invoke: subtasks = $subtasks")

    if (!isCompleted && subtasks.isNotEmpty() && isExpanded) {
        subtasks.forEach { subtask ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                SimpleTaskRow(
                    text = subtask.content,
                    checked = subtask.completed,
                    onCheckedChange = { onChecked(subtask, it) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.SimpleTaskRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.SimpleTaskRow", "Invoke: text = $text, checked = $checked")

    val textColor = if (checked) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onSurface
    }

    val textDecoration = if (checked) TextDecoration.LineThrough else null

    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.text,
        color = textColor,
        textDecoration = textDecoration,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    )
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = TasksListContract.State(
                tasksList = getTasksListPreview(),
                checkedList = getCheckedTasksListPreview(),
                isCheckedTasksExpanded = true
            ),
            onActionSent = {},
            onBackClick = {}
        )
    }
}
