package com.currency.exchanger.data.favourite

import com.currency.exchanger.data.favourite.cache.FavouriteTable

class FavouriteCacheToDataMapper {

    fun map(table: FavouriteTable) = FavouriteData(table.name)
}