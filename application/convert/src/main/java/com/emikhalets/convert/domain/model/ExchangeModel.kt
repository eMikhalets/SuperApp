package com.emikhalets.convert.domain.model

import com.emikhalets.core.common.LongZero
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.database.convert.table_exchanges.ExchangeDb
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ExchangeModel(
    val id: Long,
    val code: String,
    val value: Double,
    val date: Long,
) {

    val main: String get() = code.take(3)
    val secondary: String get() = code.takeLast(3)

    constructor(code: String) : this(0, code, 0.0, 0)

    constructor(main: String, sub: String) : this(0, "$main$sub", 0.0, 0)

    fun withValue(value: Double?, date: Long): ExchangeModel {
        return value?.let { this.copy(value = value, date = date) } ?: this
    }

    fun containsPair(base: String, currency: String): Boolean {
        return (main == base && secondary == currency) || (main == currency && secondary == base)
    }

    fun calculate(base: String, value: Long): Long {
        return when (base) {
            main -> (value * this.value).toLong()
            secondary -> (value * (1 / this.value)).toLong()
            else -> 0
        }
    }

    fun isNeedUpdate(): Boolean {
        if (code.isEmpty() || code.length == 3) return false
        if (date == LongZero) return true
        val startOfNextDay = date
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < Date().time
    }

    companion object {

        fun ExchangeModel.toDb(): ExchangeDb {
            return ExchangeDb(
                id = id,
                code = code,
                value = value,
                date = date
            )
        }

        fun List<ExchangeModel>.toDbList(): List<ExchangeDb> {
            return map { it.toDb() }
        }

        fun Flow<List<ExchangeModel>>.toDbFlow(): Flow<List<ExchangeDb>> {
            return map { it.toDbList() }
        }

        fun ExchangeDb.toModel(): ExchangeModel {
            return ExchangeModel(
                id = id,
                code = code,
                value = value,
                date = date
            )
        }

        fun List<ExchangeDb>.toModelList(): List<ExchangeModel> {
            return map { it.toModel() }
        }

        fun Flow<List<ExchangeDb>>.toModelFlow(): Flow<List<ExchangeModel>> {
            return map { it.toModelList() }
        }
    }
}

fun List<ExchangeModel>.getUpdatedDate(): Long {
    return minOf { it.date }
}
