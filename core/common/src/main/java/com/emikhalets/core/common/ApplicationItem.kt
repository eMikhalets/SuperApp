package com.emikhalets.core.common

import androidx.annotation.StringRes

sealed class ApplicationItem(
    @StringRes val appNameRes: Int,
    val enabled: Boolean,
) {

    object Events : ApplicationItem(R.string.application_events, true)
    object Finances : ApplicationItem(R.string.application_finances, true)
    object Fitness : ApplicationItem(R.string.application_fitness, true)
    object MediaLib : ApplicationItem(R.string.application_medialib, true)
    object Notes : ApplicationItem(R.string.application_notes, true)

    companion object {

        fun values(): List<ApplicationItem> {
            val list = mutableListOf<ApplicationItem>()
            if (Events.enabled) list.add(Events)
            if (Finances.enabled) list.add(Finances)
            if (Fitness.enabled) list.add(Fitness)
            if (MediaLib.enabled) list.add(MediaLib)
            if (Notes.enabled) list.add(Notes)
            return list
        }
    }
}
