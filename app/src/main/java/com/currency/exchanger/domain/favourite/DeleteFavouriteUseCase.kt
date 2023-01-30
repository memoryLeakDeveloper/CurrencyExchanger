package com.currency.exchanger.domain.favourite

import com.currency.exchanger.data.favourite.FavouriteData

interface DeleteFavouriteUseCase {

    suspend fun invoke(data: FavouriteData)
}