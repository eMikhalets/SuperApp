//package com.emikhalets.feature.workout.presentation.programs
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Icon
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Add
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.emikhalets.core.common.AppCode
//import com.emikhalets.core.ui.component.AppCard
//import com.emikhalets.core.ui.component.AppContent
//import com.emikhalets.core.ui.component.AppDivider
//import com.emikhalets.core.ui.dialog.AppErrorDialog
//import com.emikhalets.core.ui.theme.AppTheme
//import com.emikhalets.feature.workout.R
//import com.emikhalets.feature.workout.domain.model.ProgramModel
//import com.emikhalets.feature.workout.presentation.programs.ProgramsContract.Action
//import com.emikhalets.feature.workout.presentation.programs.ProgramsContract.Effect
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.collectLatest
//
//@Composable
//fun ProgramsScreen(
//    navigateToProgram: (id: Long) -> Unit,
//    navigateBack: () -> Unit,
//    viewModel: ProgramsViewModel,
//) {
//    val state by viewModel.state.collectAsState()
//
//    ScreenContent(
//        state = state,
//        onActionSent = viewModel::setAction,
//        onBackClick = navigateBack
//    )
//
//    CollectingEffect(
//        flow = viewModel.effect,
//        navigateToProgram = navigateToProgram,
//        navigateBack = navigateBack
//    )
//}
//
//@Composable
//private fun CollectingEffect(
//    flow: Flow<Effect>,
//    navigateToProgram: (id: Long) -> Unit,
//    navigateBack: () -> Unit,
//) {
//    LaunchedEffect(Unit) {
//        flow.collectLatest { effect ->
//            when (effect) {
//                Effect.NavigateToNewProgram -> navigateToProgram(AppCode.IdNull)
//                is Effect.NavigateToProgram -> navigateToProgram(effect.id)
//            }
//        }
//    }
//}
//
//@Composable
//private fun ScreenContent(
//    state: ProgramsContract.State,
//    onActionSent: (Action) -> Unit,
//    onBackClick: () -> Unit,
//) {
//    AppContent(R.string.feature_workout, onBackClick) {
//        ProgramsList(
//            programs = state.programs,
//            onProgramClick = { onActionSent(Action.ProgramClick(it.id)) },
//            onAddProgramClick = { onActionSent(Action.AddProgramClick) }
//        )
//    }
//
//    AppErrorDialog(message = state.error)
//}
//
//@Composable
//private fun ProgramsList(
//    programs: List<ProgramModel>,
//    onProgramClick: (ProgramModel) -> Unit,
//    onAddProgramClick: () -> Unit,
//) {
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(programs, key = { it.id }) { entity ->
//            ProgramBox(
//                entity = entity,
//                onProgramClick = onProgramClick,
//                modifier = Modifier.padding(8.dp, 4.dp)
//            )
//        }
//        if (programs.isNotEmpty()) {
//            item {
//                AppDivider()
//            }
//        }
//        item {
//            AddProgramBox(
//                onClick = onAddProgramClick,
//                modifier = Modifier.padding(8.dp, 4.dp)
//            )
//        }
//    }
//}
//
//@Composable
//private fun ProgramBox(
//    entity: ProgramModel,
//    onProgramClick: (ProgramModel) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    AppCard(
//        onClick = { onProgramClick(entity) },
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp, 4.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp, 8.dp)
//        ) {
//            Text(
//                text = entity.name,
//                style = MaterialTheme.typography.h5,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//@Composable
//private fun AddProgramBox(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    AppCard(
//        onClick = onClick,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp, 4.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(24.dp)
//            )
//            Text(
//                text = stringResource(R.string.feature_workout_add_program),
//                style = MaterialTheme.typography.h5,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    AppTheme {
//        ScreenContent(
//            state = ProgramsContract.State(
//                programs = getProgramsPreview()
//            ),
//            onActionSent = {},
//            onBackClick = {}
//        )
//    }
//}
