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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.date.formatFullWithWeekDate
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.AppToast
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.component.AppButton
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppLinearLoader
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.textSub
import com.emikhalets.notes.domain.R
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Action
import java.util.Date

private const val TAG = "NoteItem"

@Composable
fun NoteItemScreen(
    navigateBack: () -> Unit,
    viewModel: NoteItemViewModel,
    noteId: Long,
) {
    logi(TAG, "Invoke: id = $noteId")

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetNote(noteId))
    }

    ScreenContent(
        state = state,
        onTitleChanged = { viewModel.setAction(Action.SetTitle(it)) },
        onContentChanged = { viewModel.setAction(Action.SetContent(it)) },
        onDeleteNoteClick = { viewModel.setAction(Action.SetDeletedEntity) },
        onSaveNoteClick = { viewModel.setAction(Action.SaveNote) },
        onBackClick = navigateBack
    )

    AppDialogDelete(
        entity = state.deletedEntity,
        onDeleteClick = { viewModel.setAction(Action.DeleteNote(it)) }
    )

    AppErrorDialog(
        message = state.error,
        onDismiss = { viewModel.setAction(Action.DropError) }
    )

    if (state.isNoteDeleted) {
        logi(TAG, "Note deleted")
        AppToast(R.string.app_notes_deleted)
        navigateBack()
    }

    if (state.isNoteSaved) {
        logi(TAG, "Note saved")
        AppToast(R.string.app_notes_saved)
        navigateBack()
    }
}

@Composable
private fun ScreenContent(
    state: NoteItemContract.State,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onDeleteNoteClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi(
        "${TAG}.ScreenContent", "Invoke:\n" +
                "title = ${state.title},\n" +
                "updateDate = ${state.date},\n" +
                "content = ${state.content},\n" +
                "isNeedSave = ${state.isNeedSave}"
    )

    AppContent(ApplicationEntity.Notes.getName(), onBackClick) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppLinearLoader(visible = state.isLoading)
            NoteEditBox(
                title = state.title,
                updateDate = state.date.formatFullWithWeekDate(),
                content = state.content,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged
            )
            if (state.isNeedSave) {
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
    logi(
        "${TAG}.NoteEditBox", "Invoke:\n" +
                "title = $title,\n" +
                "updateDate = $updateDate,\n" +
                "content = $content"
    )

    AppCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            AppTextField(
                value = title,
                onValueChange = onTitleChanged,
                fontSize = 20.sp,
                placeholder = stringResource(R.string.app_notes_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = updateDate,
                style = MaterialTheme.typography.textSub,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)
            )
            AppTextField(
                value = content,
                onValueChange = onContentChanged,
                fontSize = 16.sp,
                placeholder = stringResource(R.string.app_notes_content),
                singleLine = false,
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Max)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = NoteItemContract.State(
                title = "Test title",
                date = Date().time,
                content = "Test content",
                isNeedSave = true,
            ),
            onTitleChanged = {},
            onContentChanged = {},
            onSaveNoteClick = {},
            onDeleteNoteClick = {},
            onBackClick = {}
        )
    }
}
