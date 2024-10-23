package com.emikhalets.superapp.core.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.PublishedWithChanges
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.superapp.core.common.R

sealed class AppFeature(
    @StringRes val nameRes: Int,
    val icon: ImageVector,
    val visible: Boolean,
) {

    data object Convert : AppFeature(
        nameRes = R.string.feature_convert,
        icon = Icons.Rounded.PublishedWithChanges,
        visible = true
    )

    data object Notes : AppFeature(
        nameRes = R.string.feature_notes,
        icon = Icons.Rounded.Description,
        visible = true
    )

    data object Salary : AppFeature(
        nameRes = R.string.feature_salary,
        icon = Icons.Rounded.MonetizationOn,
        visible = true
    )
}

fun getApplicationFeatures(): List<AppFeature> {
    return listOf(
        AppFeature.Convert,
        AppFeature.Notes,
        AppFeature.Salary
    )
}
