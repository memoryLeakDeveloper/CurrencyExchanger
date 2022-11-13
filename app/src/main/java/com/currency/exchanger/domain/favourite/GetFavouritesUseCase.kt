package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.FavouriteCacheToDataMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val cacheToDataMapper: FavouriteCacheToDataMapper,
) {

    fun invoke() = repository.getAll().map { it.map { table -> cacheToDataMapper.map(table) } }
}