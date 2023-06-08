package com.emikhalets.common

import android.content.Context

private const val CODE_ERROR_INTERNAL = 2000

enum class AppError(val code: Int, val messageRes: Int) {

    Internal(CODE_ERROR_INTERNAL, R.string.error_internal);
}

fun AppError.getMessage(context: Context): String {
    return context.getString(messageRes)
}
