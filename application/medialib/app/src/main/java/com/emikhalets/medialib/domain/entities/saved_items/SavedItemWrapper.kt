package com.emikhalets.medialib.domain.entities.saved_items

import androidx.compose.runtime.Composable
import com.emikhalets.medialib.domain.entities.crew.CrewEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.ratings.CrewType.Companion.getTypeName
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity
import com.emikhalets.medialib.utils.FIELD_N_A

data class SavedItemWrapper(
    val savedItem: SavedItemEntity,
    val genres: List<GenreEntity>,
    val ratings: List<RatingEntity>,
    val crew: List<CrewEntity>,
) {

    fun formatGenres(): String {
        return genres.joinToString(separator = ", ") { it.name }
    }

    fun formatRatings(): List<String> {
        return ratings.map { "${it.source}: ${it.value}" }
    }

    @Composable
    fun formatCrew(): List<String> {
        return crew.map { "${it.type.getTypeName()}: ${it.name}" }
    }

    fun formatListItemInfo(): String {
        return buildString {
            if (savedItem.runtime.isNotEmpty() && savedItem.runtime != FIELD_N_A) {
                append(savedItem.runtime)
            }
            if (savedItem.year > 0) {
                if (this.isNotEmpty()) append("  -  ")
                append(savedItem.year.toString())
            }
            if (genres.isNotEmpty()) {
                if (this.isNotEmpty()) append("  -  ")
                append(genres.joinToString { it.name })
            }
        }
    }

    fun formatDetailsInfo(): String {
        return buildString {
            if (savedItem.runtime.isNotEmpty()) {
                append(savedItem.runtime)
            }
            if (savedItem.year > 0) {
                if (this.isNotEmpty()) append("  -  ")
                append(savedItem.year.toString())
            }
        }
    }
}