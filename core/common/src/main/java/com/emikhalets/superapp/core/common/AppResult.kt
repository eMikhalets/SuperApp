package com.emikhalets.superapp.core.common

import com.emikhalets.superapp.core.common.constant.Const
import timber.log.Timber

sealed class AppResult<out S> {

    class Success<out S>(
        val data: S,
        val code: Int,
    ) : AppResult<S>()

    class Failure(
        val message: StringValue,
        val code: Int,
        val exception: Exception?,
    ) : AppResult<Nothing>()

    companion object {

        fun <S> success(
            data: S,
            code: Int = Const.CODE_SUCCESS,
        ): AppResult<S> {
            return Success(data, code)
        }

        fun failure(
            message: StringValue,
            code: Int = Const.CODE_FAILURE,
            exception: Exception? = null,
        ): AppResult<Nothing> {
            return Failure(message, code, exception)
        }
    }
}

suspend fun <T> invoke(block: suspend () -> T): AppResult<T> {
    return try {
        val result = block()
        AppResult.success(result)
    } catch (e: Exception) {
        Timber.e(e)
        AppResult.failure(StringValue.internalError(), exception = e)
    }
}

fun <S, R> AppResult<S>.map(block: (S) -> R): AppResult<R> {
    return when (this) {
        is AppResult.Failure -> AppResult.failure(message)
        is AppResult.Success -> AppResult.success(block(data))
    }
}

fun AppResult<Boolean>.getOrTrue(): Boolean {
    return when (this) {
        is AppResult.Failure -> true
        is AppResult.Success -> data
    }
}
