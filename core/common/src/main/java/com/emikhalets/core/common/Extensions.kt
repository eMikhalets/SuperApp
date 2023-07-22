package com.emikhalets.core.common

val <T : Any> List<T>.classNames: String
    get() = joinToString(", ") { it -> it::class.simpleName ?: "-" }
