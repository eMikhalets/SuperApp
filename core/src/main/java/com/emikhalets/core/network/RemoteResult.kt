package com.emikhalets.core.network

import com.emikhalets.core.common.CODE_EMPTY
import com.emikhalets.core.common.CODE_FAILURE
import com.emikhalets.core.common.CODE_SUCCESS
import com.emikhalets.core.ui.StringValue
import timber.log.Timber

sealed class RemoteResult<out S> {

    class Success<out S>(val data: S, val status: Int) : RemoteResult<S>()

    class Empty(val status: Int) : RemoteResult<Nothing>()

    class Failure(val message: StringValue, val status: Int) : RemoteResult<Nothing>()

    companion object {

        fun <S> success(data: S, status: Int = CODE_SUCCESS): RemoteResult<S> {
            return Success(data, status)
        }

        fun empty(): RemoteResult<Nothing> {
            return Empty(CODE_EMPTY)
        }

        fun failure(message: StringValue, status: Int = CODE_FAILURE): RemoteResult<Nothing> {
            return Failure(message, status)
        }
    }
}

suspend fun <T> invokeRemote(block: suspend () -> T): RemoteResult<T> {
    return try {
        val result = block()
        RemoteResult.success(result)
    } catch (e: Exception) {
        Timber.e(e)
        RemoteResult.failure(StringValue.create())
    }
}

fun <S, R> RemoteResult<S>.map(block: (S) -> R): RemoteResult<R> {
    return when (this) {
        is RemoteResult.Empty -> RemoteResult.empty()
        is RemoteResult.Failure -> RemoteResult.failure(message)
        is RemoteResult.Success -> RemoteResult.success(block(data))
    }
}
