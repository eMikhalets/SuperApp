package com.emikhalets.feature.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.date.formatFullWithWeekDate
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.component.AppBottomBox
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.textSub
import com.emikhalets.feature.notes.R
import com.emikhalets.feature.notes.data.NoteModel
import java.util.Date
import kotlinx.coroutines.launch

private const val TAG = "TaskEdit"

@Composable
fun NoteEditDialog(
    note: NoteModel?,
    onDoneClick: (NoteModel) -> Unit,
    onDismiss: () -> Unit = {},
) {
    logi(TAG, "Invoke: note = $note")

    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var needSave by remember { mutableStateOf(false) }

    val date by remember {
        val result = (note?.updateDate ?: Date().time).formatFullWithWeekDate()
        mutableStateOf(result)
    }

    AppBottomBox(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            AppTextField(
                value = title,
                onValueChange = {
                    title = it
                    needSave = true
                },
                singleLine = true,
                placeholder = stringResource(R.string.feature_notes_title),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            Text(
                text = date,
                style = MaterialTheme.typography.textSub,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AppTextField(
                value = content,
                onValueChange = {
                    content = it
                    needSave = true
                },
                placeholder = stringResource(R.string.feature_notes_content),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        scope.launch { scrollState.scrollTo(scrollState.maxValue) }
                    }
            )
            AppTextButton(
                text = stringResource(id = R.string.feature_notes_done),
                enabled = content.isNotBlank(),
                onClick = { onDoneClick(note.getNewModel(title, content)) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

private fun NoteModel?.getNewModel(title: String, content: String): NoteModel {
    return this?.copy(title = title, content = content, updateDate = Date().time)
        ?: NoteModel(title, content)
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        NoteEditDialog(
            note = NoteModel("Some note title", "Some note content"),
            onDismiss = {},
            onDoneClick = {}
        )
    }
}