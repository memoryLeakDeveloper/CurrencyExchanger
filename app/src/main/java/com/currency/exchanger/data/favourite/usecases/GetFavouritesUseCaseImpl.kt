package com.currency.exchanger.data.favourite.usecases

import com.currency.exchanger.data.favourite.FavouriteCacheToDataMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import com.currency.exchanger.domain.favourite.GetFavouritesUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavouritesUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val cacheToDataMapper: FavouriteCacheToDataMapper,
) : GetFavouritesUseCase {

    override fun invoke() = repository.getAll().map { it.map { table -> cacheToDataMapper.map(table) } }
}