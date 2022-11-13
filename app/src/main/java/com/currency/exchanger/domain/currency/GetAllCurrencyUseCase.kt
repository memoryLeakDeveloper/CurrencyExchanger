package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.CurrencyRepositoryImpl
import javax.inject.Inject

class GetAllCurrencyUseCase @Inject constructor(private val repository: CurrencyRepositoryImpl) {

    suspend fun invoke(query: String?) = repository.getAllCurrency(query)
}