package com.emikhalets.notes.presentation.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.component.AppDialog
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.domain.entity.TaskEntity

@Composable
fun TaskDialog(
    task: TaskEntity,
    onDismiss: () -> Unit,
    onDoneClick: (TaskEntity) -> Unit,
) {
    var taskContent by remember { mutableStateOf(task.content) }

    AppDialog(onDismiss = onDismiss, cancelable = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTextField(
                value = taskContent,
                onValueChange = { taskContent = it },
                onDoneClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            AppTextButton(
                text = stringResource(id = R.string.app_notes_done),
                enabled = taskContent.isNotBlank(),
                onClick = { onDoneClick(task.copy(content = taskContent)) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 32.dp)
            )
        }
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