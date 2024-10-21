//package com.emikhalets.superapp.feature.notes.ui.task_list
//
//import android.view.KeyEvent
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.key.onKeyEvent
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.unit.dp
//import com.emikhalets.superapp.core.common.constant.Const
//import com.emikhalets.superapp.core.common.timestamp
//import com.emikhalets.superapp.core.ui.component.TextFieldBorderless
//import com.emikhalets.superapp.core.ui.dialog.DialogTwoAction
//import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
//import com.emikhalets.superapp.core.ui.theme.AppTheme
//import com.emikhalets.superapp.feature.notes.domain.TaskModel
//
//@Composable
//internal fun TaskEditDialog(
//    task: TaskModel?,
//    onSaveClick: (TaskModel) -> Unit,
//    onCancelClick: () -> Unit = {},
//) {
//    task ?: return
//
//    val leftText = if (task.id == Const.IdNew) {
//        stringResource(com.emikhalets.superapp.core.common.R.string.cancel)
//    } else {
//        stringResource(com.emikhalets.superapp.core.common.R.string.delete)
//    }
//
//    var mainContent by remember { mutableStateOf(task.header) }
//    val subContent = remember {
//        val data = task.subtasks.map { Pair(it.id, it.text) }
//        val list = mutableStateListOf<Pair<Long, String>>()
//        list.addAll(data)
//        list
//    }
//
//    DialogTwoAction(
//        leftText = leftText,
//        rightText = stringResource(com.emikhalets.superapp.core.common.R.string.save),
//        dismissOnBackPress = true,
//        onLeftClick = { onCancelClick() },
//        onRightClick = { onSaveClick(task.updateTask(mainContent, subContent)) },
//    ) {
//        TextFieldBorderless(
//            value = mainContent,
//            onValueChange = { mainContent = it },
//            singleLine = true,
//            leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
//            options = KeyboardOptions(
//                capitalization = KeyboardCapitalization.Sentences,
//                imeAction = ImeAction.Next
//            ),
//            actions = KeyboardActions(
//                onNext = {
//                    if (mainContent.isNotBlank()) subContent.createSubtask()
//                }
//            ),
//            modifier = Modifier.fillMaxWidth(),
//        )
//        if (subContent.isNotEmpty()) {
//            subContent.forEachIndexed { index, item ->
//                val subtask = subContent[index]
//                TextFieldBorderless(
//                    value = item.second,
//                    onValueChange = { subContent[index] = Pair(subtask.first, it) },
//                    singleLine = true,
//                    leadingIcon = Icons.Rounded.CheckBoxOutlineBlank,
//                    options = KeyboardOptions(
//                        capitalization = KeyboardCapitalization.Sentences,
//                        imeAction = ImeAction.Next
//                    ),
//                    actions = KeyboardActions(
//                        onNext = {
//                            if (subContent[index].second.isNotBlank()) subContent.createSubtask()
//                        }
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 24.dp)
//                        .onKeyEvent {
//                            val keyEquals = it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
//                            if (keyEquals && item.second.isBlank()) subContent.removeAt(index)
//                            true
//                        }
//                )
//            }
//        }
//    }
//}
//
//private fun TaskModel.updateTask(
//    mainContent: String,
//    subContent: List<Pair<Long, String>>,
//): TaskModel {
//    val newSubtasks = subContent.mapNotNull { item ->
//        this.subtasks
//            .find { it.id == item.first }
//            ?.copy(content = item.second, updateDate = timestamp())
//            ?: TaskModel(
//                id = Const.IdNew,
//                parentId = this.id,
//                content = item.second,
//                completed = false,
//                createDate = timestamp(),
//                updateDate = timestamp(),
//                subtasks = emptyList()
//            )
//    }
//    return this.copy(content = mainContent, subtasks = newSubtasks)
//}
//
//private fun MutableList<Pair<Long, String>>.createSubtask() {
//    add(Pair(Const.IdNew, ""))
//}
//
//@ScreenPreview
//@Composable
//private fun Preview() {
//    AppTheme {
//        TaskEditDialog(
//            task = TaskModel(
//                id = 1,
//                parentId = 0,
//                content = "Some task content",
//                completed = false,
//                createDate = timestamp(),
//                updateDate = timestamp(),
//                subtasks = listOf(
//                    TaskModel(
//                        id = 2,
//                        parentId = 1,
//                        content = "Some task content",
//                        completed = false,
//                        createDate = timestamp(),
//                        updateDate = timestamp(),
//                        subtasks = emptyList()
//                    ),
//                    TaskModel(
//                        id = 3,
//                        parentId = 1,
//                        content = "Some task content",
//                        completed = false,
//                        createDate = timestamp(),
//                        updateDate = timestamp(),
//                        subtasks = emptyList()
//                    )
//                )
//            ),
//            onCancelClick = {},
//            onSaveClick = {}
//        )
//    }
//}
