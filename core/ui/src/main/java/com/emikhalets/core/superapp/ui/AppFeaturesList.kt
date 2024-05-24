package com.emikhalets.core.superapp.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.superapp.core.common.R

sealed class AppFeature(
    @StringRes val nameRes: Int,
    val icon: ImageVector,
    val visible: Boolean,
) {

    data object Salary : AppFeature(
        nameRes = R.string.common_feature_salary,
        icon = Icons.Rounded.MonetizationOn,
        visible = true
    )
}

fun getApplicationFeatures(): List<AppFeature> {
    return listOf(
        AppFeature.Salary
    )
}
