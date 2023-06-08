package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.core.R
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.entity.NoteEntity

@Composable
fun EditNoteDialog(
    entity: NoteEntity?,
    onSaveClick: (entity: NoteEntity?) -> Unit,
    onDismiss: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester.Default }
    val focusManager = LocalFocusManager.current

    var title: String by remember { mutableStateOf(entity?.title ?: "") }
    var content: String by remember { mutableStateOf(entity?.content ?: "") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            AppTextField(
                value = title,
                onValueChange = { title = it },
                maxLines = 1,
                capitalization = KeyboardCapitalization.Sentences,
                onDoneClick = { focusManager.moveFocus(FocusDirection.Next) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .focusRequester(focusRequester)
            )
            AppTextField(
                value = content,
                onValueChange = { content = it },
                capitalization = KeyboardCapitalization.Sentences,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
            )
            Divider(modifier = Modifier.padding(16.dp))
            AppTextButton(
                text = stringResource(R.string.app_save),
                onClick = {
                    val note = entity?.copy(title = title, content = content)
                        ?: NoteEntity(title, content)
                    onSaveClick(note)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        EditNoteDialog(
            entity = NoteEntity("test title", "test content"),
            onSaveClick = {},
            onDismiss = {}
        )
    }
}