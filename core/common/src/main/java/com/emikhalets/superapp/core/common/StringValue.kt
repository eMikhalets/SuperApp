package com.emikhalets.superapp.core.common

import android.content.Context

sealed class StringValue {

    data object Empty : StringValue()
    data object InternalError : StringValue()
    class Message(val text: String) : StringValue()
    class Resource(val resId: Int, vararg val args: Any) : StringValue()
    class Exception(val throwable: Throwable?) : StringValue()

    companion object {

        /**
         * Возвращает [StringValue.Empty]
         */
        fun empty(): StringValue {
            return Empty
        }

        /**
         * Возвращает [StringValue.InternalError]
         */
        fun internalError(): StringValue {
            return InternalError
        }

        /**
         * Возвращает [StringValue.Message] или [StringValue.Empty]
         */
        fun message(message: String?): StringValue {
            return if (message.isNullOrBlank()) {
                Empty
            } else {
                Message(message)
            }
        }

        /**
         * Возвращает [StringValue.Resource]
         */
        fun resource(stringRes: Int, vararg args: Any): StringValue {
            return Resource(stringRes, args)
        }

        /**
         * Возвращает [StringValue.Exception]
         */
        fun exception(throwable: Throwable?): StringValue {
            return Exception(throwable)
        }

        fun StringValue?.asString(context: Context?): String {
            val internal = context?.getString(R.string.error_internal) ?: ""
            context ?: return internal
            return when (this) {
                Empty -> ""
                InternalError -> internal
                is Message -> text
                is Resource -> context.getString(resId, *args)
                is Exception -> throwable?.message ?: internal
                else -> internal
            }
        }
    }
}