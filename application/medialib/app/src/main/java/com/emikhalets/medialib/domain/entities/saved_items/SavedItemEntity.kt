package com.emikhalets.medialib.domain.entities.saved_items

abstract class SavedItemEntity(
    val id: Long,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val year: Int,
    val poster: String,
    val status: SavedItemStatus,
    val rating: Int,
    val runtime: String,
)
