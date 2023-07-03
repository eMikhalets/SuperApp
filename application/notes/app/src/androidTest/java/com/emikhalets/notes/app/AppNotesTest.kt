package com.emikhalets.notes.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.notes.presentation.screens.note_item.NoteItemScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListScreen
import com.emikhalets.notes.presentation.screens.notes.NotesListViewModel
import org.junit.Rule
import org.junit.Test

class AppNotesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openNotesListAndClickOnNoteAndChangeContent() {
        val viewModel = NotesListViewModel(NotesUseCaseTest())
        composeTestRule.setContent {
            val navController = rememberNavController()
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppTheme {
                NavHost(navController, AppNotesDestination.Notes) {
                    composable(AppNotesDestination.Notes) {
                        NotesListScreen(
                            navigateBack = { navController.popBackStack() },
                            navigateToNote = { navController.navigate(AppNotesDestination.noteWithArgs(it)) },
                            viewModel = viewModel
                        )
                    }
                    composable(AppNotesDestination.NoteWithArgs, AppNotesDestination.noteArgsList) {
                        NoteItemScreen(
                            navigateBack = { navController.popBackStack(AppNotesDestination.Notes, false) },
                            viewModel = hiltViewModel(),
                            noteId = AppNotesDestination.getNoteArgsId(it)
                        )
                    }
                }
            }
        }
        composeTestRule.onNodeWithText("Note content 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note content 1").performClick()
        composeTestRule.onNodeWithText("Note content 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note content 1").performClick()
        composeTestRule.onNodeWithText("Note content 1").performTextReplacement("Note content new")
        composeTestRule.onNodeWithText("Note content new").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("Note content new").assertIsDisplayed()
    }
}