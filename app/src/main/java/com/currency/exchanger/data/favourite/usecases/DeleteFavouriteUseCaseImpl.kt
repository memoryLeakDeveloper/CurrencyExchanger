package com.currency.exchanger.data.favourite.usecases

import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import com.currency.exchanger.domain.favourite.DeleteFavouriteUseCase
import javax.inject.Inject

class DeleteFavouriteUseCaseImpl @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val dataToCacheMapper: FavouriteDataToCacheMapper
) : DeleteFavouriteUseCase {

    override suspend fun invoke(data: FavouriteData) = repository.delete(dataToCacheMapper.map(data))
}