package com.emikhalets.core.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface AppDao<T> {

    @Insert
    suspend fun insert(item: T): Long

    @Insert
    suspend fun insert(list: List<T>)

    @Update
    suspend fun update(item: T)

    @Update
    suspend fun update(list: List<T>)

    @Delete
    suspend fun delete(item: T)
}