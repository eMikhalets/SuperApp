package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppDialogDelete
import com.emikhalets.core.ui.component.AppDialogMessage
import com.emikhalets.core.ui.component.AppFloatButton
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Action
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Effect

@Composable
fun NotesListScreen(
    navigateToNote: (id: Long?) -> Unit,
    navigateBack: () -> Unit,
    viewModel: NotesListViewModel,
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetNotes)
    }

    ScreenContent(
        notesList = state.notesList,
        onNoteClick = { navigateToNote(it) },
        onDeleteNoteClick = { viewModel.setAction(Action.DeleteNoteDialog(it)) },
        onAddNoteClick = { navigateToNote(null) },
        onBackClick = navigateBack
    )

    when (effect) {
        is Effect.Error -> AppDialogMessage((effect as Effect.Error).message)


        is Effect.DeleteNoteDialog -> {
            AppDialogDelete(
                entity = (effect as Effect.DeleteNoteDialog).entity,
                onDeleteClick = { viewModel.setAction(Action.DeleteNote(it)) }
            )
        }

        is Effect.NavigateToNewNote -> {
            navigateToNote((effect as Effect.NavigateToNewNote).id)
        }

        null -> Unit
    }
}

@Composable
private fun ScreenContent(
    notesList: List<NoteEntity>,
    onNoteClick: (Long) -> Unit,
    onDeleteNoteClick: (NoteEntity) -> Unit,
    onAddNoteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppChildScreenBox(onBackClick, stringResource(com.emikhalets.core.R.string.application_notes)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(notesList) { entity ->
                    NoteBox(
                        entity = entity,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNoteClick(entity.id) }
                            .padding(8.dp)
                    )
                }
            }
            AppFloatButton(
                icon = Icons.Rounded.Add,
                onClick = { onAddNoteClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun NoteBox(entity: NoteEntity, modifier: Modifier = Modifier) {
    Card(
        elevation = 0.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = entity.title,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = entity.content,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Text(
                text = entity.updateTimestamp.toString(),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            notesList = getNotesListPreview(),
            onNoteClick = {},
            onAddNoteClick = {},
            onDeleteNoteClick = {},
            onBackClick = {}
        )
    }
}
