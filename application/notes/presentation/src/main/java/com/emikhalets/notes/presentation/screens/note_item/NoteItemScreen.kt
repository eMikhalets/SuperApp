package com.emikhalets.notes.presentation.screens.note_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.ApplicationItem
import com.emikhalets.core.common.date.formatWithPattern
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.component.AppButton
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Action
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Effect

@Composable
fun NoteItemScreen(
    navigateBack: () -> Unit,
    viewModel: NoteItemViewModel,
    noteId: Long,
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(0)

    var title by remember { mutableStateOf("") }
    var updateDate by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isNeedSave by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetNote(noteId))
    }

    LaunchedEffect(state.noteEntity) {
        title = state.noteEntity?.title ?: ""
        updateDate = state.noteEntity?.updateTimestamp.formatWithPattern("EEEE, dd MMMM yyyy")
        content = state.noteEntity?.content ?: ""
    }

    ScreenContent(
        title = title,
        updateDate = updateDate,
        content = content,
        isNeedSave = isNeedSave,
        onTitleChanged = {
            isNeedSave = true
            title = it
        },
        onContentChanged = {
            isNeedSave = true
            content = it
        },
        onDeleteNoteClick = { viewModel.setAction(Action.DeleteNoteDialog) },
        onSaveNoteClick = {
            val newEntity = state.noteEntity?.copy(title = title, content = content)
                ?: com.emikhalets.notes.domain.entity.NoteEntity(title, content)
            viewModel.setAction(Action.SaveNote(newEntity))
        },
        onBackClick = navigateBack
    )

    when (effect) {
        is Effect.Error -> {
            AppDialogMessage(message = (effect as Effect.Error).message)
        }

        Effect.NoteSaved -> {
            AppToast(R.string.app_notes_saved)
            navigateBack()
        }

        Effect.NoteDeleted -> {
            AppToast(R.string.app_notes_deleted)
            navigateBack()
        }

        Effect.DeleteNoteDialog -> {
            AppDialogDelete(
                entity = null,
                onDeleteClick = { viewModel.setAction(Action.DeleteNote) }
            )
        }
    }
}

@Composable
private fun ScreenContent(
    title: String,
    updateDate: String,
    content: String,
    isNeedSave: Boolean,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onDeleteNoteClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppChildScreenBox(onBackClick, stringResource(ApplicationItem.Notes.appNameRes)) {
        Box(modifier = Modifier.fillMaxSize()) {
            NoteEditBox(
                title = title,
                updateDate = updateDate,
                content = content,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                modifier = Modifier.fillMaxSize()
            )
            if (isNeedSave) {
                AppButton(
                    text = stringResource(R.string.app_notes_save),
                    onClick = onSaveNoteClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(40.dp)
                )
            }
        }
    }
}

@Composable
private fun NoteEditBox(
    title: String,
    updateDate: String,
    content: String,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        com.emikhalets.core.ui.component.AppTextField(
            value = title,
            onValueChange = onTitleChanged,
            fontSize = 20.sp,
            placeholder = stringResource(R.string.app_notes_title),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = updateDate,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        )
        com.emikhalets.core.ui.component.AppTextField(
            value = content,
            onValueChange = onContentChanged,
            placeholder = stringResource(R.string.app_notes_content),
            singleLine = false,
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    com.emikhalets.core.ui.theme.AppTheme {
        ScreenContent(
            title = "Test title",
            updateDate = "Test update date",
            content = "Test content",
            isNeedSave = true,
            onTitleChanged = {},
            onContentChanged = {},
            onSaveNoteClick = {},
            onDeleteNoteClick = {},
            onBackClick = {}
        )
    }
}
