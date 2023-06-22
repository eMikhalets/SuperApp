package com.emikhalets.notes.presentation.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.ui.component.AppScaffold
import com.emikhalets.core.ui.theme.AppTheme

@Composable
fun NotesApplication(
    navGraph: NavGraphBuilder?,
    navHostController: NavHostController,
) {
    val scaffoldState = rememberScaffoldState()

    AppScaffold(
        navController = navHostController,
        scaffoldState = scaffoldState,
        bottomBarList = listOf(NotesScreen.NotesList, NotesScreen.Settings)
    ) {
        navGraph?.applicationNotes(navHostController)
    }
}
