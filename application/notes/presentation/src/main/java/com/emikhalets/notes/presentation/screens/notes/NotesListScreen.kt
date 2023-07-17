package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.common.date.formatShortWithWeekDate
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppFloatButtonBox
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.core.ui.theme.textSub
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Action

private const val TAG = "NotesList"

@Composable
fun NotesListScreen(
    navigateToNote: (id: Long?) -> Unit,
    navigateBack: () -> Unit,
    viewModel: NotesListViewModel,
) {
    logi(TAG, "Invoke")

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetNotes)
    }

    ScreenContent(
        state = state,
        onNoteClick = { navigateToNote(it.id) },
        onAddNoteClick = { navigateToNote(AppCode.IdNull) },
        onBackClick = navigateBack
    )

    AppErrorDialog(
        message = state.error,
        onDismiss = { viewModel.setAction(Action.DropError) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScreenContent(
    state: NotesListContract.State,
    onNoteClick: (NoteEntity) -> Unit,
    onAddNoteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi("${TAG}.ScreenContent", "Invoke: state = $state")

    AppContent(ApplicationEntity.Notes.getName(), onBackClick) {
        AppFloatButtonBox(Icons.Rounded.Add, onAddNoteClick) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(state.notesList) { entity ->
                    NoteBox(
                        entity = entity,
                        onNoteClick = onNoteClick
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteBox(
    entity: NoteEntity,
    onNoteClick: (NoteEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("${TAG}.NoteBox", "Invoke: entity = $entity")

    AppCard(
        header = entity.title,
        headerSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNoteClick(entity) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = entity.content,
                style = MaterialTheme.typography.text,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(vertical = 8.dp)
            )
            Text(
                text = entity.updateTimestamp.formatShortWithWeekDate(),
                style = MaterialTheme.typography.textSub,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = NotesListContract.State(
                notesList = getNotesListPreview(),
                isLoading = true
            ),
            onNoteClick = {},
            onAddNoteClick = {},
            onBackClick = {}
        )
    }
}
