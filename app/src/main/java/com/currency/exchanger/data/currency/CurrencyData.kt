package com.currency.exchanger.data.currency

data class CurrencyData(
    val name: String,
    val rate: Double,
    val isFavourite: Boolean
)