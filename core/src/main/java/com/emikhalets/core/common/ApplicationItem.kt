package com.emikhalets.core.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.emikhalets.core.R

sealed class ApplicationItem(@StringRes val name: Int, @DrawableRes val icon: Int) {

    data object Convert : ApplicationItem(
        name = R.string.core_application_convert,
        icon = R.drawable.ic_round_currency_exchange_24
    )

    data object Salaries : ApplicationItem(
        name = R.string.core_application_salaries,
        icon = R.drawable.ic_round_currency_exchange_24
    )

    companion object {

        fun getAll(): List<ApplicationItem> {
            return listOf(Convert, Salaries)
        }
    }
}
