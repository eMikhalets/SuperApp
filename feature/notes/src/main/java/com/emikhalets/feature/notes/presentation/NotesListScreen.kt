package com.emikhalets.feature.notes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.date.formatShortWithWeekDate
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppFloatButtonBox
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.core.ui.theme.textSub
import com.emikhalets.feature.notes.R
import com.emikhalets.feature.notes.data.NoteModel
import com.emikhalets.feature.notes.presentation.NotesListContract.Action

private const val TAG = "NotesList"

@Composable
fun NotesListScreen(
    navigateBack: () -> Unit,
    viewModel: NotesListViewModel,
) {
    logi(TAG, "Invoke")
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onActionSent = viewModel::setAction,
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    state: NotesListContract.State,
    onActionSent: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: state = $state")

    AppContent(R.string.feature_notes, onBackClick) {
        AppFloatButtonBox(Icons.Rounded.Add, { onActionSent(Action.NoteClicked()) }) {
            NotesList(
                notesList = state.notesList,
                onNoteClick = { onActionSent(Action.NoteClicked(it)) },
                onDeleteNote = { onActionSent(Action.SetDeletingNote(it)) },
            )
        }
    }

    if (state.showNoteDialog) {
        NoteEditDialog(
            note = state.editingNote,
            onDoneClick = { onActionSent(Action.SaveNote(it)) },
            onDismiss = { onActionSent(Action.DropNoteDialog) }
        )
    }

    AppDialogDelete(
        entity = state.deletingNote,
        onDeleteClick = { onActionSent(Action.DeleteNote) },
        onDismiss = { onActionSent(Action.DropDeleteDialog) }
    )

    AppErrorDialog(
        message = state.error,
        onDismiss = { onActionSent(Action.DropError) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NotesList(
    notesList: List<NoteModel>,
    onNoteClick: (NoteModel) -> Unit,
    onDeleteNote: (NoteModel) -> Unit,
) {
    logi("$TAG.NotesList", "Invoke: list = $notesList")

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(notesList) { item ->
            NoteBox(
                model = item,
                onNoteClick = onNoteClick,
                onDeleteNote = onDeleteNote
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteBox(
    model: NoteModel,
    onNoteClick: (NoteModel) -> Unit,
    onDeleteNote: (NoteModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("${TAG}.NoteBox", "Invoke: model = $model")

    AppCard(
        header = model.title,
        headerSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onNoteClick(model) },
                onDoubleClick = { onDeleteNote(model) }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = model.content,
                style = MaterialTheme.typography.text,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(vertical = 8.dp)
            )
            Text(
                text = model.updateDate.formatShortWithWeekDate(),
                style = MaterialTheme.typography.textSub,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = NotesListContract.State(
                notesList = getNotesListPreview(),
            ),
            onActionSent = {},
            onBackClick = {}
        )
    }
}
