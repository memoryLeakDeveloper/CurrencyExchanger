package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import javax.inject.Inject

class DeleteFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val dataToCacheMapper: FavouriteDataToCacheMapper
) {

    suspend fun invoke(data: FavouriteData) = repository.delete(dataToCacheMapper.map(data))
}