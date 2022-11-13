package com.currency.exchanger.data.favourite

import com.currency.exchanger.data.favourite.cache.FavouriteTable

class FavouriteDataToCacheMapper {

    fun map(table: FavouriteData) = FavouriteTable(table.name)

}