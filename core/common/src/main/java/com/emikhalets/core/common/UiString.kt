package com.emikhalets.core.common

import android.content.Context
import android.content.res.Resources

sealed class UiString {

    data class Message(val value: String?) : UiString()

    class Resource(val resId: Int, vararg val args: Any) : UiString()

    companion object {

        fun create(): UiString = Resource(R.string.error_internal)

        fun create(message: String?): UiString = Message(message)

        fun create(stringRes: Int): UiString = Resource(stringRes)
    }
}

fun UiString?.asString(context: Context): String = when (this) {
    is UiString.Message -> value ?: Resources.getSystem().getString(R.string.error_internal)
    is UiString.Resource -> context.getString(resId, *args)
    else -> Resources.getSystem().getString(R.string.error_internal)
}