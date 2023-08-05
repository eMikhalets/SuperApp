package com.emikhalets.feature.workout.presentation.program_add

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.core.ui.component.AppTextButton
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppDialog
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.feature.workout.R
import com.emikhalets.feature.workout.domain.model.ExerciseModel
import com.emikhalets.feature.workout.domain.model.WorkoutModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WorkoutAddDialog(
    onSaveClick: (WorkoutModel) -> Unit,
    onDismiss: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    val exercises = remember { mutableStateListOf<ExerciseModel>() }

    AppDialog(onDismiss = onDismiss, cancelable = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                placeholder = stringResource(R.string.feature_workout_tap_enter_exercise),
                keyboardOptions = getKeyboardOptions(),
                keyboardActions = KeyboardActions(
                    onNext = {
                        exercises.createExercise()
                        focusManager.moveFocusDown(scope)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            exercises.forEachIndexed { index, item ->
                AppTextField(
                    value = item.name,
                    onValueChange = { exercises[index] = item.copy(name = it) },
                    singleLine = true,
                    placeholder = stringResource(R.string.feature_workout_tap_enter_exercise),
                    keyboardOptions = getKeyboardOptions(),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            exercises.createExercise(index)
                            focusManager.moveFocusDown(scope)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL) {
                                if (item.name.isEmpty()) {
                                    focusManager.moveFocus(FocusDirection.Up)
                                    exercises.removeAt(index)
                                }
                            }
                            true
                        }
                )
            }
            AppTextButton(
                text = stringResource(id = R.string.feature_workout_save),
                enabled = name.isNotBlank(),
                onClick = { onSaveClick(WorkoutModel(name, exercises)) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 32.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

private fun getKeyboardOptions(): KeyboardOptions {
    return KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        imeAction = ImeAction.Next
    )
}

private fun MutableList<ExerciseModel>.createExercise(index: Int = -1) {
    if (index < -1) return
    add(index + 1, ExerciseModel())
}

private fun FocusManager.moveFocusDown(scope: CoroutineScope) {
    scope.launch {
        delay(100)
        moveFocus(FocusDirection.Down)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        WorkoutAddDialog(
            onDismiss = {},
            onSaveClick = {}
        )
    }
}