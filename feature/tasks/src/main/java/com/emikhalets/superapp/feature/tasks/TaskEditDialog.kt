package com.emikhalets.superapp.feature.tasks

import android.view.KeyEvent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.ui.dialog.DialogOneAction
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.rectangle
import com.emikhalets.superapp.domain.tasks.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun TaskEditDialog(
    task: TaskModel?,
    onSaveClick: (TaskModel) -> Unit,
    onDismiss: () -> Unit = {},
) {
    task ?: return

    var mainContent by remember { mutableStateOf(task.content) }
    val subContent = remember {
        val data = task.subtasks.map { Pair(it.id, it.content) }
        val list = mutableStateListOf<Pair<Long, String>>()
        list.addAll(data)
        list
    }

    DialogOneAction(
        actionText = stringResource(R.string.tasks_save),
        onDismiss = onDismiss,
        onConfirm = { onSaveClick(task.updateTask(mainContent, subContent)) }
    ) {
        TaskTextField(
            value = mainContent,
            onValueChange = { mainContent = it },
            onNextClick = { subContent.add(Pair(Const.IdNew, "")) },
            modifier = Modifier.fillMaxWidth()
        )
        if (subContent.isNotEmpty()) {
            subContent.forEachIndexed { index, item ->
                val subtask = subContent[index]
                TaskTextField(
                    value = item.second,
                    onValueChange = { subContent[index] = Pair(subtask.first, it) },
                    onNextClick = { subContent.add(Pair(Const.IdNew, "")) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                                && item.second.isBlank()
                            ) {
                                subContent.removeAt(index)
                            }
                            true
                        }
                )
            }
        }
    }
}

private fun TaskModel.updateTask(content: String, subs: List<Pair<Long, String>>): TaskModel {
    val newSubtasks = subs.mapNotNull { item ->
        val oldTask = this.subtasks.find { it.id == item.first }
        oldTask?.copy(content = item.second)
    }
    return this.copy(content = content, subtasks = newSubtasks)
}

private fun MutableList<TaskModel>.createSubtask(index: Int = -1) {
    if (index < -1) return
    add(index + 1, TaskModel())
}

private fun FocusManager.moveFocusDown(scope: CoroutineScope) {
    scope.launch {
        delay(100)
        moveFocus(FocusDirection.Down)
    }
}


@Composable
private fun TaskTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    onNextClick: () -> Unit = {},
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = MaterialTheme.shapes.rectangle,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(onNext = { onNextClick() }),
        modifier = modifier.fillMaxWidth()
    )
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        TaskEditDialog(
            task = TaskModel(
                id = 1,
                parentId = 0,
                content = "Some task content",
                completed = false,
                createDate = DateHelper.now,
                updateDate = DateHelper.now,
                subtasks = listOf(
                    TaskModel(
                        id = 2,
                        parentId = 1,
                        content = "Some task content",
                        completed = false,
                        createDate = DateHelper.now,
                        updateDate = DateHelper.now,
                        subtasks = emptyList()
                    ),
                    TaskModel(
                        id = 3,
                        parentId = 1,
                        content = "Some task content",
                        completed = false,
                        createDate = DateHelper.now,
                        updateDate = DateHelper.now,
                        subtasks = emptyList()
                    )
                )
            ),
            onDismiss = {},
            onSaveClick = {}
        )
    }
}
