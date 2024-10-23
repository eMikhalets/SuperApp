package com.emikhalets.superapp.feature.notes.ui.task_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.ui.component.TextFieldPrimary
import com.emikhalets.superapp.core.ui.dialog.DialogTwoAction
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.notes.domain.TaskModel
import timber.log.Timber

@Composable
internal fun TaskEditDialog(
    task: TaskModel?,
    onFirstClick: (TaskModel) -> Unit,
    onCancelClick: () -> Unit = {},
) {
    task ?: return

    val leftText = if (task.id == Const.IdNew) {
        stringResource(com.emikhalets.superapp.core.common.R.string.cancel)
    } else {
        stringResource(com.emikhalets.superapp.core.common.R.string.delete)
    }

    val focusRequester = remember { FocusRequester() }
    var content by remember { mutableStateOf(task.header) }

    DialogTwoAction(
        leftText = leftText,
        rightText = stringResource(com.emikhalets.superapp.core.common.R.string.save),
        dismissOnBackPress = true,
        onLeftClick = { onCancelClick() },
        onRightClick = { onFirstClick(task.copy(header = content)) },
    ) {
        TextFieldPrimary(
            value = content,
            onValueChange = { content = it },
            singleLine = true,
            options = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            actions = KeyboardActions(
                onDone = { focusRequester.freeFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
    }

    LaunchedEffect(Unit) {
        try {
            focusRequester.requestFocus()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        TaskEditDialog(
            task = TaskModel(header = "Some task content"),
            onCancelClick = {},
            onFirstClick = {}
        )
    }
}
