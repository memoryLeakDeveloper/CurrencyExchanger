package com.currency.exchanger.data.currency.usecases

import com.currency.exchanger.data.currency.CurrencyRepositoryImpl
import com.currency.exchanger.domain.currency.GetAllCurrencyUseCase
import javax.inject.Inject

class GetAllCurrencyUseCaseImpl @Inject constructor(private val repository: CurrencyRepositoryImpl) : GetAllCurrencyUseCase {

    override suspend fun invoke(query: String?) = repository.getAllCurrency(query)
}