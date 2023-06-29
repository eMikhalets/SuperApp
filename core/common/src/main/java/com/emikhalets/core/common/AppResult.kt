package com.emikhalets.core.common

sealed class AppResult<out T> {

    class Success<T>(val code: Int, val data: T) : AppResult<T>()

    class Failure(val code: Int, val message: UiString?) : AppResult<Nothing>()

    val isSuccess: Boolean get() = this !is Failure

    val isFailure: Boolean get() = this is Failure

    companion object {

        fun <T> success(data: T, code: Int = AppCode.SUCCESS): AppResult<T> {
            return Success(code, data)
        }

        fun <T> failure(message: UiString?, code: Int = AppCode.FAILURE): AppResult<T> {
            return Failure(code, message)
        }
    }
}

inline fun <R> execute(block: () -> R): AppResult<R> {
    return try {
        AppResult.success(block())
    } catch (e: Throwable) {
        AppResult.failure(UiString.create(e.message))
    }
}

inline fun <T> AppResult<T>.onFailure(action: (code: Int, message: UiString?) -> Unit): AppResult<T> {
    if (this.isFailure) {
        val result = this as? AppResult.Failure ?: return this
        action(result.code, result.message)
    }
    return this
}

inline fun <T> AppResult<T>.onSuccess(action: (data: T) -> Unit): AppResult<T> {
    if (isSuccess) {
        val data = (this as? AppResult.Success)?.data ?: return this
        action(data)
    }
    return this
}
