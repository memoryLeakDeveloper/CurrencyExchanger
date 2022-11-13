package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import javax.inject.Inject

class SaveFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepositoryImpl,
    private val dataToCacheMapper: FavouriteDataToCacheMapper
) {

    suspend fun invoke(data: FavouriteData) = repository.save(dataToCacheMapper.map(data))
}