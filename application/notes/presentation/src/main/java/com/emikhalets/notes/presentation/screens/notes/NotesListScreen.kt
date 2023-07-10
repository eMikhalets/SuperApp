package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.AppCode
import com.emikhalets.core.common.ApplicationItem.Notes.appNameRes
import com.emikhalets.core.common.date.formatShortWithWeekDate
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppFloatButton
import com.emikhalets.core.ui.dialog.AppDialogDelete
import com.emikhalets.core.ui.dialog.AppDialogMessage
import com.emikhalets.core.ui.theme.AppTheme
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

    var deletedEntity by remember { mutableStateOf<NoteEntity?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetNotes)
    }

    ScreenContent(
        notesList = state.notesList,
        onNoteClick = { navigateToNote(it) },
        onDeleteNoteClick = { deletedEntity = it },
        onAddNoteClick = { navigateToNote(AppCode.IdNull) },
        onBackClick = navigateBack
    )

    if (state.error != null) {
        logi(TAG, "Show error dialog")
        AppDialogMessage(state.error, { viewModel.setAction(Action.DropError) })
    }

    val deleted = deletedEntity
    if (deleted != null) {
        logi(TAG, "Show delete note dialog: entity = $deleted")
        AppDialogDelete(
            entity = deleted,
            onDeleteClick = { viewModel.setAction(Action.DeleteNote(it)) }
        )
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
    logi("${TAG}.ScreenContent", "Invoke: list = $notesList")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
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
                            .height(180.dp)
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { onNoteClick(entity.id) }
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
    logi("${TAG}.NoteBox", "Invoke: entity = $entity")
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
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = entity.content,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .weight(1f)
            )
            Text(
                text = entity.updateTimestamp.formatShortWithWeekDate(),
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
