package com.emikhalets.core.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface AppDao<T> {

    @Insert
    suspend fun insert(item: T): Long

    @Insert
    suspend fun insert(list: List<T>): List<Long>

    @Update
    suspend fun update(item: T): Int

    @Update
    suspend fun update(list: List<T>): Int

    @Delete
    suspend fun delete(item: T): Int
}