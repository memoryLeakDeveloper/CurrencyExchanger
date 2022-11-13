package com.currency.exchanger.data.favourite.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class FavouriteTable(
    @PrimaryKey
    val name: String
)