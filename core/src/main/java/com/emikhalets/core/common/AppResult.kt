@file:OptIn(ExperimentalContracts::class)

package com.emikhalets.core.common

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class AppResult<out T> constructor(val value: Any?) {

    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean get() = value is Failure

    fun exceptionOrNull(): Pair<Int, String>? = when (value) {
        is Failure -> Pair(value.code, value.message)
        else -> null
    }

    companion object {

        fun <T> success(data: T?, code: Int = AppCode.SUCCESS): AppResult<T> =
            AppResult(createSuccess(code, data))

        fun <T> failure(message: String?, code: Int = AppCode.FAILURE): AppResult<T> =
            AppResult(createFailure(code, message ?: ""))
    }

    internal data class Success<T>(val code: Int, val data: T)

    internal data class Failure(val code: Int, val message: String)
}

internal fun <T> createSuccess(code: Int, data: T): Any = AppResult.Success(code, data)

internal fun createFailure(code: Int, message: String): Any = AppResult.Failure(code, message)

inline fun <R> execute(block: () -> R): AppResult<R> {
    return try {
        AppResult.success(block())
    } catch (e: Throwable) {
        AppResult.failure(e.message)
    }
}

inline fun <T, R> T.runCatching(block: T.() -> R): AppResult<R> {
    return try {
        AppResult.success(block())
    } catch (e: Throwable) {
        loge("Error", e.message)
        AppResult.failure(e.message)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <R, T> AppResult<T>.map(transform: (value: T) -> R): AppResult<R> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when {
        isSuccess -> AppResult.success(transform(value as T))
        else -> AppResult(value)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <R, T> AppResult<T>.mapCatching(transform: (value: T) -> R): AppResult<R> {
    return when {
        isSuccess -> runCatching { transform(value as T) }
        else -> AppResult(value)
    }
}

inline fun <T> AppResult<T>.onFailure(action: (code: Int, message: String) -> Unit): AppResult<T> {
    contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
    exceptionOrNull()?.let { (code, message) -> action(code, message) }
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <T> AppResult<T>.onSuccess(action: (data: T) -> Unit): AppResult<T> {
    contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
    if (isSuccess) action(value as T)
    return this
}
