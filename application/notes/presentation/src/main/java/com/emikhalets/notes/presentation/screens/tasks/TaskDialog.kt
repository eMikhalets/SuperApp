package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.domain.entity.SubtaskEntity
import com.emikhalets.notes.domain.entity.TaskEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "TaskDialog"

@Composable
fun TaskDialog(
    task: TaskEntity,
    onDismiss: () -> Unit,
    onDoneClick: (TaskEntity) -> Unit,
) {
    logi(TAG, "Invoke: task = $task")
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    var taskContent by remember { mutableStateOf(task.content) }
    val subtasks = remember { task.subtasks.toMutableStateList() }

    AppDialog(onDismiss = onDismiss, cancelable = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTextField(
                value = taskContent,
                onValueChange = { taskContent = it },
                onDoneClick = {
                    subtasks.add(SubtaskEntity(task.id, ""))
                    scope.launch {
                        delay(100)
//                        it.moveFocus(FocusDirection.Down)
                    }
                },
                placeholder = stringResource(R.string.app_notes_tap_enter_subtask),
                leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            if (subtasks.isNotEmpty()) {
                subtasks.forEachIndexed { index, item ->
                    AppTextField(
                        value = item.content,
                        onValueChange = { subtasks[index] = item.copy(content = it) },
                        onDoneClick = {
                            subtasks.add(SubtaskEntity(task.id, ""))
                            scope.launch {
                                delay(100)
//                                it.moveFocus(FocusDirection.Down)
                            }
                        },
                        onBackspaceEvent = {
//                            it.moveFocus(FocusDirection.Up)
                            if (item.content.isEmpty()) subtasks.removeAt(index)
                        },
                        placeholder = stringResource(R.string.app_notes_tap_enter_subtask),
                        leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    )
                }
            }
            AppTextButton(
                text = stringResource(id = R.string.app_notes_done),
                enabled = taskContent.isNotBlank(),
                onClick = { onDoneClick(task.copy(content = taskContent, subtasks = subtasks)) },
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
        TaskDialog(
            task = TaskEntity(content = "Some task content"),
            onDismiss = {},
            onDoneClick = {}
        )
    }
}