package com.currency.exchanger.di.currency

import com.currency.exchanger.data.currency.CurrencyRepositoryImpl
import com.currency.exchanger.data.currency.cloud.CurrencyCloudToDataMapper
import com.currency.exchanger.domain.currency.GetAllCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CurrencyModule {

    @Provides
    fun provideGetAllCurrencyUseCase(repository: CurrencyRepositoryImpl) = GetAllCurrencyUseCase(repository)

    @Provides
    fun provideMapper() = CurrencyCloudToDataMapper()
}