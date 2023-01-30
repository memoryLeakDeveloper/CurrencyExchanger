package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.FavouriteData
import kotlinx.coroutines.flow.Flow

interface GetFavouritesUseCase {

    fun invoke(): Flow<List<FavouriteData>>
}