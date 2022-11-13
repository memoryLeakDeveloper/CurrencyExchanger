package com.currency.exchanger.di.favourite

import com.currency.exchanger.core.cache.AppDatabase
import com.currency.exchanger.data.favourite.FavouriteCacheToDataMapper
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import com.currency.exchanger.domain.favourite.DeleteFavouriteUseCase
import com.currency.exchanger.domain.favourite.GetFavouritesUseCase
import com.currency.exchanger.domain.favourite.SaveFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteModule {

    @Provides
    fun provideDeleteFavouriteUseCase(repository: FavouriteRepositoryImpl, mapper: FavouriteDataToCacheMapper) =
        DeleteFavouriteUseCase(repository, mapper)

    @Provides
    fun provideGetFavouriteUseCase(repository: FavouriteRepositoryImpl, mapper: FavouriteCacheToDataMapper) =
        GetFavouritesUseCase(repository, mapper)

    @Provides
    fun provideSaveFavouriteUseCase(repository: FavouriteRepositoryImpl, mapper: FavouriteDataToCacheMapper) =
        SaveFavouriteUseCase(repository, mapper)

    @Provides
    fun provideCacheToDataMapper() = FavouriteCacheToDataMapper()

    @Provides
    fun provideDataToCacheMapper() = FavouriteDataToCacheMapper()

    @Singleton
    @Provides
    fun provideCurrencyDao(database: AppDatabase) = database.currencyDao
}