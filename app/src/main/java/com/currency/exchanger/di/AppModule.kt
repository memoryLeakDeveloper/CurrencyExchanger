package com.currency.exchanger.di

import android.content.Context
import com.currency.exchanger.core.cache.AppDatabase
import com.currency.exchanger.core.cloud.ApiService
import com.currency.exchanger.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApi() = ApiService().create()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = DataStoreManager(context)
}