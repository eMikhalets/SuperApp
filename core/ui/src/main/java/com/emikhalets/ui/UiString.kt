package com.emikhalets.ui

import android.content.Context

sealed class UiString {

    data class Message(
        val value: String?,
    ) : UiString()

    class Resource(
        val resId: Int,
        vararg val args: Any,
    ) : UiString()

    fun asString(context: Context?): String = when (this) {
        is Message -> value ?: context?.getString(R.string.error_internal) ?: ""
        is Resource -> context?.getString(resId, *args) ?: ""
    }

    companion object {

        fun create(): UiString = Resource(R.string.error_internal)

        fun create(resource: Int): UiString = Resource(resource)

        fun create(message: String): UiString = Message(message)
    }
}
