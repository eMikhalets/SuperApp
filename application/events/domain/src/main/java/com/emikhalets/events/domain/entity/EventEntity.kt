package com.emikhalets.events.domain.entity

import android.content.Context
import com.emikhalets.common.DateHelper

data class EventEntity(
    val id: Long,
    val groupId: Long,
    val name: String,
    val type: EventType,
    val date: Long,
    val note: String,
    val isWithYear: Boolean,
    val isHide: Boolean,
    val isAlarm: Boolean,
    val days: Int,
    val age: Int,
) {

    companion object {

        @Composable
        fun EventEntity.formatHomeInfo(): String {
            val date = date.formatDateThisYear("EE, d MMM")
            val type = stringResource(eventType.nameRes)
            val turns = stringResource(R.string.event_list_item_turns, age)
            return if (age == 0 || withoutYear) "$date • $type" else "$date • $type • $turns"
        }

        fun EventEntity.formatNotificationInfo(context: Context): String {
            val type = context.getString(eventType.nameRes)
            val turns = context.getString(R.string.notification_turns, age)
            return if (age == 0 || withoutYear) "$type • $name" else "$type • $name • $turns"
        }

    }
}
