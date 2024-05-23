package com.emikhalets.superapp.core.common.date

// TODO удалить?
fun Long.startOfNextDay(): Long {
    return localDate().atStartOfDay().plusDays(1).timestamp()
}
