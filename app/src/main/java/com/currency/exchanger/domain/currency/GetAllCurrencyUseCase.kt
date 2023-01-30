package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.CurrencyResponse
import kotlinx.coroutines.flow.Flow

interface GetAllCurrencyUseCase {

    suspend fun invoke(query: String?): Flow<CurrencyResponse>
}