package com.currency.exchanger.di.currency

import com.currency.exchanger.data.currency.CurrencyRepositoryImpl
import com.currency.exchanger.data.currency.cloud.CurrencyCloudToDataMapper
import com.currency.exchanger.data.currency.usecases.GetAllCurrencyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CurrencyModule {

    @Provides
    fun provideGetAllCurrencyUseCaseImpl(repository: CurrencyRepositoryImpl) = GetAllCurrencyUseCaseImpl(repository)

    @Provides
    fun provideMapper() = CurrencyCloudToDataMapper()
}