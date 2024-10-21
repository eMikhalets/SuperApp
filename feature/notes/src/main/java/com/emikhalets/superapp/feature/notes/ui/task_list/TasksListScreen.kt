package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import com.emikhalets.superapp.core.ui.component.TextPrimary
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksContract.Action
import com.emikhalets.superapp.feature.notes.ui.task_list.TasksContract.State

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
    Column(modifier = Modifier.fillMaxSize()) {
        TasksList(
            list = state.tasksList,
            onExpandClick = {},
            onTaskClick = {},
            onCheck = { onSetAction(Action.CheckTask(it)) },
            modifier = Modifier.weight(1f)
        )
        AddCard(
            onClick = { onSetAction(Action.SetEditTask(TaskModel())) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp, 12.dp, 24.dp)
        )
    }

//    TaskEditDialog(
//        task = state.editTask,
//        onSaveClick = { onSetAction(Action.SaveEditTask(it)) },
//        onCancelClick = {
//            if (state.editTask?.id == Const.IdNew) {
//                onSetAction(Action.SetEditTask(null))
//            } else {
//                onSetAction(Action.DeleteTask(state.editTask))
//            }
//        }
//    )
}

@Composable
private fun AddCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.clickableOnce { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun TasksList(
    list: List<TaskModel>,
    onExpandClick: (TaskModel) -> Unit,
    onTaskClick: (SubTaskModel) -> Unit,
    onCheck: (SubTaskModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(list) { task ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp, 12.dp, 0.dp)
            ) {
                Column {
                    TaskHeader(
                        text = task.header,
                        onExpandClick = {},
                        onAddClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    task.subtasks.forEach { subtask ->
                        TaskRow(
                            task = subtask,
                            onChecked = onCheck,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableOnce { onTaskClick(subtask) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskHeader(
    text: String,
    onExpandClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onExpandClick() },
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 12.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier
                .clickable { onAddClick() }
                .padding(16.dp, 12.dp)
                .size(32.dp)
        )
    }
}

@Composable
private fun TaskRow(
    task: SubTaskModel,
    onChecked: (SubTaskModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textDecoration = if (task.completed) TextDecoration.LineThrough else null
    val textColor = if (task.completed) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onBackground
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Checkbox(
            checked = task.completed,
            enabled = !task.completed,
            onCheckedChange = { onChecked(task) },
        )
        TextPrimary(
            text = task.text,
            color = textColor,
            fontSize = 16.sp,
            textDecoration = textDecoration,
            modifier = Modifier.padding(end = 12.dp)
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
