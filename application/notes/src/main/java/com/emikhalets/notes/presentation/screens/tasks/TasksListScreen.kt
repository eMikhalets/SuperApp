package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.simplenotes.R
import com.emikhalets.simplenotes.presentation.theme.AppTheme
import com.emikhalets.simplenotes.utils.toast

@Composable
fun TasksListScreen(
    checkedTasksVisible: Boolean,
    viewModel: TasksListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var taskEntity by remember { mutableStateOf<com.emikhalets.notes.domain.entity.TaskEntity?>(null) }
    var checkedTasksCollapsed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllTasks()
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    ScreenContent(
        tasksList = state.tasksList,
        checkedTasksList = state.checkedList,
        checkedTasksVisible = checkedTasksVisible,
        checkedTasksCollapsed = checkedTasksCollapsed,
        onTaskClick = { entity -> taskEntity = entity },
        onCheckTask = { entity, checked -> viewModel.updateTask(entity, checked) },
        onCheckSubtask = { task, subtask, checked ->
            viewModel.updateSubtask(task, subtask, checked)
        },
        onAddTaskClick = { taskEntity = com.emikhalets.notes.domain.entity.TaskEntity.empty() },
        onCollapseTasksClick = { checkedTasksCollapsed = !checkedTasksCollapsed },
    )

    if (taskEntity != null) {
        TaskDialog(
            task = taskEntity!!,
            onDismiss = { taskEntity = null },
            onDoneClick = { task ->
                taskEntity = null
                viewModel.updateTask(task)
            }
        )
    }
}

@Composable
private fun ScreenContent(
    tasksList: List<com.emikhalets.notes.domain.entity.TasksListWrapper>,
    checkedTasksList: List<com.emikhalets.notes.domain.entity.TasksListWrapper>,
    checkedTasksVisible: Boolean,
    checkedTasksCollapsed: Boolean,
    onTaskClick: (com.emikhalets.notes.domain.entity.TaskEntity) -> Unit,
    onCheckTask: (com.emikhalets.notes.domain.entity.TaskEntity, Boolean) -> Unit,
    onCheckSubtask: (com.emikhalets.notes.domain.entity.TaskEntity, com.emikhalets.notes.domain.entity.SubtaskEntity, Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCollapseTasksClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(tasksList) { entity ->
                    TaskBox(
                        entity = entity,
                        onTaskClick = onTaskClick,
                        onCheckTask = onCheckTask,
                        onCheckSubtask = onCheckSubtask,
                    )
                }
                if (checkedTasksVisible && checkedTasksList.isNotEmpty()) {
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
                                onTaskClick = onTaskClick,
                                onCheckTask = onCheckTask,
                                onCheckSubtask = onCheckSubtask,
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { onAddTaskClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Composable
private fun CompletedTasksHeader(
    tasksSize: Int,
    checkedTasksCollapsed: Boolean,
    onCollapseTasksClick: () -> Unit,
) {
    val collapsedTasksIcon = if (checkedTasksCollapsed) Icons.Default.KeyboardArrowUp
    else Icons.Default.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCollapseTasksClick() }
            .padding(start = 28.dp)
    ) {
        Text(
            text = stringResource(R.string.tasks_list_completed, tasksSize),
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = collapsedTasksIcon,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
private fun TaskBox(
    entity: com.emikhalets.notes.domain.entity.TasksListWrapper,
    onTaskClick: (com.emikhalets.notes.domain.entity.TaskEntity) -> Unit,
    onCheckTask: (com.emikhalets.notes.domain.entity.TaskEntity, Boolean) -> Unit,
    onCheckSubtask: (com.emikhalets.notes.domain.entity.TaskEntity, com.emikhalets.notes.domain.entity.SubtaskEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(entity.task) }
            .padding(8.dp, 4.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 2.dp)
            ) {
                Checkbox(
                    checked = entity.task.checked,
                    onCheckedChange = { onCheckTask(entity.task, it) }
                )
                Text(
                    text = entity.task.content,
                    color = if (entity.task.checked) Color.Gray else Color.Unspecified,
                    textDecoration = if (entity.task.checked) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                if (entity.task.subtasks.isNotEmpty()) {
                    TaskCounterBox(
                        entity = entity,
                        expanded = expanded,
                        onExpandClick = { expanded = it },
                    )
                }
            }
            if (expanded) {
                entity.task.subtasks.forEach { subtask ->
                    SubtaskBox(
                        entity = subtask,
                        onCheckSubtask = { checked ->
                            onCheckSubtask(entity.task, subtask, checked)
                        },
                        modifier = Modifier.padding(start = 28.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskCounterBox(
    entity: com.emikhalets.notes.domain.entity.TasksListWrapper,
    expanded: Boolean,
    onExpandClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onExpandClick(!expanded) }
    ) {
        Text(
            text = "${entity.subtasksCheckedCount}/${entity.subtasksCount}",
            color = Color.Gray,
        )
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowDown
                } else {
                    Icons.Default.KeyboardArrowUp
                },
                contentDescription = null
            )
        }
    }
}

@Composable
private fun SubtaskBox(
    entity: com.emikhalets.notes.domain.entity.SubtaskEntity,
    onCheckSubtask: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
    ) {
        Checkbox(
            checked = entity.checked,
            onCheckedChange = onCheckSubtask
        )
        Text(
            text = entity.content,
            color = if (entity.checked) Color.Gray else Color.Unspecified,
            textDecoration = if (entity.checked) TextDecoration.LineThrough else null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        ScreenContent(
            tasksList = listOf(
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content",
                        subtasks = listOf(
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content"
                            ),
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content"
                            ),
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content",
                                checked = true
                            ),
                        )
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content"
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content"
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content"
                    )
                ),
            ),
            checkedTasksList = listOf(
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content",
                        checked = true,
                        subtasks = listOf(
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content",
                                checked = true
                            ),
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content",
                                checked = true
                            ),
                            com.emikhalets.notes.domain.entity.SubtaskEntity(
                                taskId = 0,
                                content = "Subtask content",
                                checked = true
                            ),
                        )
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content",
                        checked = true
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content",
                        checked = true
                    )
                ),
                com.emikhalets.notes.domain.entity.TasksListWrapper(
                    com.emikhalets.notes.domain.entity.TaskEntity(
                        content = "Task content",
                        checked = true
                    )
                ),
            ),
            checkedTasksVisible = true,
            checkedTasksCollapsed = false,
            onTaskClick = {},
            onCheckTask = { _, _ -> },
            onCheckSubtask = { _, _, _ -> },
            onAddTaskClick = {},
            onCollapseTasksClick = {},
        )
    }
}
