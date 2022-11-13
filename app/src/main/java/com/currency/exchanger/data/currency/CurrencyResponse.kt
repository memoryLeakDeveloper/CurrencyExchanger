package com.currency.exchanger.data.currency

sealed class CurrencyResponse {
    data class Success(val data: CurrencyResponseData) : CurrencyResponse()
    data class Failure(val e: Throwable) : CurrencyResponse()
    object Loading : CurrencyResponse()
}
