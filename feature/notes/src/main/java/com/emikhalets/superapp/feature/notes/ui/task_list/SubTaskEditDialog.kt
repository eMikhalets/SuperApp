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
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel

@Composable
internal fun SubTaskEditDialog(
    task: SubTaskModel?,
    onFirstClick: (SubTaskModel) -> Unit,
    onCancelClick: () -> Unit = {},
) {
    task ?: return

    val leftText = if (task.id == Const.IdNew) {
        stringResource(com.emikhalets.superapp.core.common.R.string.cancel)
    } else {
        stringResource(com.emikhalets.superapp.core.common.R.string.delete)
    }

    val focusRequester = FocusRequester.Default
    var content by remember { mutableStateOf(task.text) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    DialogTwoAction(
        leftText = leftText,
        rightText = stringResource(com.emikhalets.superapp.core.common.R.string.save),
        dismissOnBackPress = true,
        onLeftClick = { onCancelClick() },
        onRightClick = { onFirstClick(task.copy(text = content)) },
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
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        SubTaskEditDialog(
            task = SubTaskModel(text = "Some task content"),
            onCancelClick = {},
            onFirstClick = {}
        )
    }
}
