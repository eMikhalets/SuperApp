package com.emikhalets.superapp.feature.salary

object AppSalariesRoute {

    const val NavGraph: String = "app_salary_graph"
    internal const val Chart: String = "app_salary_chart"
    internal const val EditItem: String = "app_salary_edit_item"

    internal const val KEY_ITEM_ID: String = "item_id"

    internal fun itemId(): String = "$EditItem/{$KEY_ITEM_ID}"

    internal fun itemId(id: Long): String = "$EditItem/$id"
}