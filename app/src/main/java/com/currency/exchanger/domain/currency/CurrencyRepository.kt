package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.CurrencyResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun getAllCurrency(query: String?): Flow<CurrencyResponse>

}