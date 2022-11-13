package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.cache.FavouriteTable
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun save(table: FavouriteTable)
    suspend fun delete(table: FavouriteTable)
    fun getAll(): Flow<List<FavouriteTable>?>
}