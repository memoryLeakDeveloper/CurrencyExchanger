package com.currency.exchanger.data.favourite.usecases

import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import com.currency.exchanger.domain.favourite.SaveFavouriteUseCase
import javax.inject.Inject

class SaveFavouriteUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val dataToCacheMapper: FavouriteDataToCacheMapper
) : SaveFavouriteUseCase {

    override suspend fun invoke(data: FavouriteData) = repository.save(dataToCacheMapper.map(data))
}