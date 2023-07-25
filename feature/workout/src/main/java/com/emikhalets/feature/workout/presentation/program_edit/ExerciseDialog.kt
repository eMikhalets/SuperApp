//package com.emikhalets.fitness.presentation.program_edit
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.emikhalets.core.common.logi
//import com.emikhalets.core.ui.component.AppTextButton
//import com.emikhalets.core.ui.component.AppTextField
//import com.emikhalets.core.ui.dialog.AppDialog
//import com.emikhalets.core.ui.theme.AppTheme
//import com.emikhalets.fitness.domain.R
//import com.emikhalets.fitness.domain.entity.ExerciseEntity
//
//private const val TAG = "ExerciseDialog"
//
//@Composable
//fun ExerciseDialog(
//    exercise: ExerciseEntity,
//    onDoneClick: (ExerciseEntity) -> Unit,
//    onDismiss: () -> Unit = {},
//) {
//    logi(TAG, "Invoke: exercise = $exercise")
//    val focusRequester = remember { FocusRequester() }
//    var name by remember { mutableStateOf(exercise.name) }
//
//    AppDialog(onDismiss = onDismiss, cancelable = true) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            AppTextField(
//                value = name,
//                onValueChange = { name = it },
////                onDoneClick = { onDoneClick(exercise.copy(name = name)) },
//                placeholder = stringResource(R.string.app_fitness_exercise_name),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .focusRequester(focusRequester)
//            )
//            AppTextButton(
//                text = stringResource(id = R.string.app_fitness_exercise_save),
//                enabled = name.isNotBlank(),
//                onClick = { onDoneClick(exercise.copy(name = name)) },
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .padding(top = 32.dp)
//            )
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ScreenPreview() {
//    AppTheme {
//        ExerciseDialog(
//            exercise = ExerciseEntity(1, "Some task content"),
//            onDismiss = {},
//            onDoneClick = {}
//        )
//    }
//}