package com.currency.exchanger.data.favourite

import com.currency.exchanger.data.favourite.cache.FavouriteDao
import com.currency.exchanger.data.favourite.cache.FavouriteTable
import com.currency.exchanger.domain.favourite.FavouriteRepository
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(private val favouriteDao: FavouriteDao) : FavouriteRepository {

    override suspend fun save(table: FavouriteTable) = favouriteDao.insert(table)

    override suspend fun delete(table: FavouriteTable) = favouriteDao.delete(table)

    override fun getAll() = favouriteDao.getAll()
}