package com.emikhalets.core.common.date

fun Long.startOfNextDay(): Long {
    return localDate().atStartOfDay().plusDays(1).timestamp()
}
