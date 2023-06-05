package com.emikhalets.simplenotes.presentation.screens.tasks_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.emikhalets.simplenotes.R
import com.emikhalets.simplenotes.domain.entities.TaskEntity
import com.emikhalets.simplenotes.presentation.core.DialogButton
import com.emikhalets.simplenotes.presentation.core.TaskTextField
import com.emikhalets.simplenotes.presentation.theme.AppTheme

@Composable
fun TaskDialog(
    task: TaskEntity,
    onDismiss: () -> Unit,
    onDoneClick: (TaskEntity) -> Unit,
) {
    val focusRequester = remember { FocusRequester.Default }

    var taskContent by remember { mutableStateOf(task.content) }

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TaskTextField(
                    text = taskContent,
                    onTextChange = { taskContent = it },
                    onDoneClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
                DialogButton(
                    text = stringResource(id = R.string.tasks_dialog_done),
                    enabled = taskContent.isNotBlank(),
                    onClick = { onDoneClick(task.copy(content = taskContent)) },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 32.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }
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