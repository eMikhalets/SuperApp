package com.emikhalets.feature.tasks.presentation

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.feature.tasks.R
import com.emikhalets.feature.tasks.data.TaskModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "TaskEdit"

@Composable
fun TaskEditDialog(
    task: TaskModel?,
    onDoneClick: (TaskModel) -> Unit,
    onDismiss: () -> Unit = {},
) {
    logi(TAG, "Invoke: task = $task")

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var taskContent by remember { mutableStateOf(task?.content ?: "") }
    val subtasks = remember { task?.subtasks?.toMutableStateList() ?: mutableStateListOf() }

    AppDialog(onDismiss = onDismiss, cancelable = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTextField(
                value = taskContent,
                onValueChange = { taskContent = it },
                placeholder = stringResource(R.string.feature_tasks_tap_enter_subtask),
                leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
                keyboardActions = KeyboardActions(onDone = {
                    if (subtasks.isEmpty()) subtasks.add(TaskModel())
                    scope.launch {
                        delay(100)
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            if (subtasks.isNotEmpty()) {
                subtasks.forEachIndexed { index, item ->
                    AppTextField(
                        value = item.content,
                        onValueChange = { subtasks[index] = item.copy(content = it) },
                        placeholder = stringResource(R.string.feature_tasks_tap_enter_subtask),
                        leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
                        keyboardActions = KeyboardActions(onDone = {
                            val isNextExist = subtasks.getOrNull(index + 1)
                                ?.let { true } ?: false
                            if (isNextExist) subtasks.add(TaskModel())
                            scope.launch {
                                delay(100)
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .onKeyEvent {
                                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL) {
                                    if (item.content.isEmpty()) {
                                        focusManager.moveFocus(FocusDirection.Up)
                                        subtasks.removeAt(index)
                                    }
                                }
                                true
                            }
                    )
                }
            }
            AppTextButton(
                text = stringResource(id = R.string.feature_tasks_done),
                enabled = taskContent.isNotBlank(),
                onClick = {
                    task?.copy(content = taskContent, subtasks = subtasks)
                        ?: TaskModel(taskContent).run(onDoneClick)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 32.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        TaskEditDialog(
            task = TaskModel(
                content = "Some task content",
                completed = false,
                subtasks = listOf(
                    TaskModel("asd"),
                    TaskModel("asd"),
                    TaskModel("asd"),
                )
            ),
            onDismiss = {},
            onDoneClick = {}
        )
    }
}