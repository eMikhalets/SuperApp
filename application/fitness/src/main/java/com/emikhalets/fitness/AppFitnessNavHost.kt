package com.emikhalets.fitness

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.emikhalets.core.navigation.AppBottomBarItem

val applicationFitnessBottomBar: List<AppBottomBarItem> = listOf()

fun NavGraphBuilder.applicationFitnessGraph(navController: NavHostController) {
//    navigation(
//        startDestination = AppFitnessDestination.Programs,
//        route = AppFitnessDestination.NavGraph
//    ) {
//        composable(AppFitnessDestination.Programs) {
//            logi(TAG, "Composable ProgramsScreen")
//            ProgramsScreen(
//                navigateToProgram = { id ->
//                    logi(TAG, "Navigate to program: id = $id")
//                    navController.navigate(AppFitnessDestination.programEditWithArgs(id))
//                },
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel()
//            )
//        }
//        composable(
//            AppFitnessDestination.ProgramWithArgs,
//            AppFitnessDestination.programArgsList
//        ) {
//            logi(TAG, "Composable ProgramScreen")
//            ProgramScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel(),
//                programId = AppFitnessArgs.getProgramId(it)
//            )
//        }
//        composable(
//            AppFitnessDestination.ProgramEditWithArgs,
//            AppFitnessDestination.programEditArgsList
//        ) {
//            logi(TAG, "Composable ProgramEditScreen")
//            ProgramEditScreen(
//                navigateBack = {
//                    logi(TAG, "Navigate back")
//                    navController.popBackStack()
//                },
//                viewModel = hiltViewModel(),
//                programId = AppFitnessArgs.getProgramId(it)
//            )
//        }
//    }
}
