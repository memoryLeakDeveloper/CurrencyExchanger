package com.currency.exchanger.data.currency

data class CurrencyResponseData(
    val success: Boolean? = null,
    val time: Long? = null,
    val base: String? = null,
    val date: String? = null,
    val rates: Map<String, Double>?,
)