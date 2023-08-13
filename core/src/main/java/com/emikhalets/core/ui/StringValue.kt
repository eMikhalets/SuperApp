package com.emikhalets.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.core.R
import com.emikhalets.core.common.StringEmpty

sealed class StringValue {

    object Empty : StringValue()
    object InternalError : StringValue()
    class Message(val text: String?) : StringValue()
    class Error(val throwable: Throwable) : StringValue()
    class Resource(val resId: Int, vararg val args: Any) : StringValue()

    companion object {

        fun create(): StringValue = InternalError
        fun create(message: String?): StringValue = Message(message)
        fun create(stringRes: Int): StringValue = Resource(stringRes)
        fun create(throwable: Throwable): StringValue = Error(throwable)
    }
}

fun StringValue?.asString(context: Context): String {
    val internal = context.getString(R.string.core_common_error_internal)
    return when (this) {
        StringValue.Empty -> StringEmpty
        StringValue.InternalError -> internal
        is StringValue.Message -> text ?: StringEmpty
        is StringValue.Error -> throwable.message ?: internal
        is StringValue.Resource -> context.getString(resId, *args)
        else -> internal
    }
}

@Composable
fun StringValue?.asString(): String {
    val internal = stringResource(R.string.core_common_error_internal)
    return when (this) {
        StringValue.Empty -> internal
        StringValue.InternalError -> internal
        is StringValue.Message -> text ?: internal
        is StringValue.Error -> throwable.message ?: internal
        is StringValue.Resource -> stringResource(resId, *args)
        else -> internal
    }
}