package com.emikhalets.core.database

import com.emikhalets.core.common.InvalidLong
import com.emikhalets.core.ui.StringValue
import timber.log.Timber

sealed class LocalResult<out S> {

    class Success<out S>(val data: S) : LocalResult<S>()

    class Failure(val message: StringValue) : LocalResult<Nothing>()

    companion object {

        fun <S> success(data: S): LocalResult<S> {
            return Success(data)
        }

        fun failure(message: StringValue): LocalResult<Nothing> {
            return Failure(message)
        }
    }
}

suspend fun <T> invokeLocal(block: suspend () -> T): LocalResult<T> {
    return try {
        val result = block()
        LocalResult.success(result)
    } catch (e: Exception) {
        Timber.e(e)
        LocalResult.failure(StringValue.create())
    }
}

fun <S, R> LocalResult<S>.map(block: (S) -> R): LocalResult<R> {
    return when (this) {
        is LocalResult.Failure -> LocalResult.failure(message)
        is LocalResult.Success -> LocalResult.success(block(data))
    }
}

fun LocalResult<Boolean>.getOrTrue(): Boolean {
    return when (this) {
        is LocalResult.Failure -> true
        is LocalResult.Success -> data
    }
}

fun LocalResult<Long>.getOrInvalidLong(): Long {
    return when (this) {
        is LocalResult.Failure -> InvalidLong
        is LocalResult.Success -> data
    }
}

fun <T> LocalResult<List<T>>.getOrEmpty(): List<T> {
    return when (this) {
        is LocalResult.Failure -> emptyList()
        is LocalResult.Success -> data
    }
}
