package com.emikhalets.medialib.domain.entities.saved_items

import android.content.res.Resources
import com.emikhalets.medialib.R

enum class SavedItemStatus {
    NONE, WATCH, WATCH_AGAIN, WATCHED, DROPPED;

    companion object {

        fun SavedItemStatus.getIconRes(): Int? {
            return when (this) {
                NONE -> null
                WATCH -> R.drawable.ic_round_bookmark_24
                WATCH_AGAIN -> R.drawable.ic_round_question_mark_24
                WATCHED -> R.drawable.ic_round_visibility_24
                DROPPED -> R.drawable.ic_round_close_24
            }
        }

        fun SavedItemStatus.getTitleRes(): Int {
            return when (this) {
                NONE -> R.string.movie_status_none
                WATCH -> R.string.movie_status_watch
                WATCH_AGAIN -> R.string.movie_status_watch_again
                WATCHED -> R.string.movie_status_watched
                DROPPED -> R.string.movie_status_dropped
            }
        }

        fun getItemStatus(title: String): SavedItemStatus {
            val context = Resources.getSystem()
            return when (title) {
                context.getString(R.string.movie_status_watch) -> WATCH
                context.getString(R.string.movie_status_watch_again) -> WATCH_AGAIN
                context.getString(R.string.movie_status_watched) -> WATCHED
                context.getString(R.string.movie_status_dropped) -> DROPPED
                else -> NONE
            }
        }

        fun getTextList(): List<Int> {
            return listOf(
                NONE.getTitleRes(),
                WATCH.getTitleRes(),
                WATCH_AGAIN.getTitleRes(),
                WATCHED.getTitleRes(),
                DROPPED.getTitleRes()
            )
        }
    }
}