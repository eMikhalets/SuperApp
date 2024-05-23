package com.emikhalets.superapp.core.common

import timber.log.Timber

sealed class AppResult<out S> {

    class Success<out S>(val data: S, val code: Int) : AppResult<S>()

    class Failure(val message: StringValue, val code: Int) : AppResult<Nothing>()

    companion object {

        fun <S> success(
            data: S,
            code: Int = AppConstant.CODE_SUCCESS,
        ): AppResult<S> {
            return Success(data, code)
        }

        fun failure(
            message: StringValue,
            code: Int = AppConstant.CODE_FAILURE,
        ): AppResult<Nothing> {
            return Failure(message, code)
        }
    }
}

suspend fun <T> invokeLocal(block: suspend () -> T): AppResult<T> {
    return try {
        val result = block()
        AppResult.success(result)
    } catch (e: Exception) {
        Timber.e(e)
        AppResult.failure(StringValue.internalError())
    }
}

fun <S, R> AppResult<S>.map(block: (S) -> R): AppResult<R> {
    return when (this) {
        is AppResult.Failure -> AppResult.failure(message)
        is AppResult.Success -> AppResult.success(block(data))
    }
}
