package com.emikhalets.core.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.R

@Immutable
sealed class ApplicationEntity(
    @StringRes val appNameRes: Int,
) {

    @Immutable
    object Convert : ApplicationEntity(R.string.application_convert)

    @Immutable
    object Events : ApplicationEntity(R.string.application_events)

    @Immutable
    object Finances : ApplicationEntity(R.string.application_finances)

    @Immutable
    object Fitness : ApplicationEntity(R.string.application_fitness)

    @Immutable
    object MediaLib : ApplicationEntity(R.string.application_medialib)

    @Immutable
    object Notes : ApplicationEntity(R.string.application_notes)

    companion object {

        fun values(): List<ApplicationEntity> {
            val list = mutableListOf<ApplicationEntity>()
            if (Convert.isVisible()) list.add(Convert)
            if (Events.isVisible()) list.add(Events)
            if (Finances.isVisible()) list.add(Finances)
            if (Fitness.isVisible()) list.add(Fitness)
            if (MediaLib.isVisible()) list.add(MediaLib)
            if (Notes.isVisible()) list.add(Notes)
            return list
        }
    }
}

fun ApplicationEntity.isEnabled(): Boolean {
    return when (this) {
        ApplicationEntity.Convert -> true
        ApplicationEntity.Events -> false
        ApplicationEntity.Finances -> false
        ApplicationEntity.Fitness -> false
        ApplicationEntity.MediaLib -> false
        ApplicationEntity.Notes -> true
    }
}

fun ApplicationEntity.isVisible(): Boolean {
    return when (this) {
        ApplicationEntity.Convert -> true
        ApplicationEntity.Events -> true
        ApplicationEntity.Finances -> true
        ApplicationEntity.Fitness -> true
        ApplicationEntity.MediaLib -> true
        ApplicationEntity.Notes -> true
    }
}