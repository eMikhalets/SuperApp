package com.emikhalets.convert.domain

import android.content.Context
import com.emikhalets.core.common.AppDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConvertDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppDataStore(context, NAME) {

    suspend fun getCurrenciesDate(block: (Long) -> Unit) {
        getLong(CURRENCIES_DATE) { block(it) }
    }

    suspend fun setCurrenciesDate(value: Long) {
        setLong(CURRENCIES_DATE, value)
    }

    companion object {

        private const val NAME = "AppConvertData"
        private const val CURRENCIES_DATE = "CURRENCIES_DATE"
    }
}