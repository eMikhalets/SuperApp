package com.emikhalets.superapp.feature.salary

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object AppSalariesRoute {

    internal const val KEY_ITEM_ID: String = "item_id"

    const val NavGraph: String = "app_salary_graph"
    internal const val Chart: String = "app_salary_chart"
    internal const val EditItem: String = "app_salary_edit_item/{$KEY_ITEM_ID}"

    internal fun editItemRoute(id: Long): String = "$EditItem/$id"

    internal fun editItemArgs(): List<NamedNavArgument> =
        listOf(navArgument(KEY_ITEM_ID) { type = NavType.LongType })
}