package com.emikhalets.simplenotes.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.simplenotes.R
import com.emikhalets.simplenotes.presentation.theme.AppTheme

@Composable
fun TaskTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.CheckBoxOutlineBlank,
            contentDescription = null,
            tint = Color.Gray
        )
        TextField(
            value = text,
            onValueChange = onTextChange,
//            TODO: uncomment when subtasks will be implemented
//            placeholder = { Text(text = stringResource(R.string.tasks_dialog_tap_for_subtask)) },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            keyboardActions = KeyboardActions(onDone = { onDoneClick() }),
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TaskTextField(
                text = "Test text",
                onTextChange = {},
                onDoneClick = {}
            )
        }
    }
}