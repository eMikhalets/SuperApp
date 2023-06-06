package com.emikhalets.ui

import android.content.res.Resources

sealed class UiString {

    data class Message(
        val value: String?,
    ) : UiString()

    class Resource(
        val resId: Int,
        vararg val args: Any,
    ) : UiString()

    fun asString(): String = when (this) {
        is Message -> value ?: Resources.getSystem().getString(R.string.error_internal)
        is Resource -> Resources.getSystem().getString(resId, *args)
    }

    companion object {

        fun create(): UiString = Resource(R.string.error_internal)
    }
}
