package com.currency.exchanger.data.favourite.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite_table")
    fun getAll(): Flow<List<FavouriteTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: FavouriteTable)

    @Delete
    suspend fun delete(data: FavouriteTable)
}