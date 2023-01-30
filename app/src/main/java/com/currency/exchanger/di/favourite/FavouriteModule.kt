package com.currency.exchanger.di.favourite

import com.currency.exchanger.core.cache.AppDatabase
import com.currency.exchanger.data.favourite.FavouriteCacheToDataMapper
import com.currency.exchanger.data.favourite.FavouriteDataToCacheMapper
import com.currency.exchanger.data.favourite.FavouriteRepositoryImpl
import com.currency.exchanger.data.favourite.usecases.DeleteFavouriteUseCaseImpl
import com.currency.exchanger.data.favourite.usecases.GetFavouritesUseCaseImpl
import com.currency.exchanger.data.favourite.usecases.SaveFavouriteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteModule {

    @Provides
    fun provideDeleteFavouriteUseCaseImpl(repository: FavouriteRepositoryImpl, mapper: FavouriteDataToCacheMapper) =
        DeleteFavouriteUseCaseImpl(repository, mapper)

    @Provides
    fun provideGetFavouriteUseCaseImpl(repository: FavouriteRepositoryImpl, mapper: FavouriteCacheToDataMapper) =
        GetFavouritesUseCaseImpl(repository, mapper)

    @Provides
    fun provideSaveFavouriteUseCaseImpl(repository: FavouriteRepositoryImpl, mapper: FavouriteDataToCacheMapper) =
        SaveFavouriteUseCaseImpl(repository, mapper)

    @Provides
    fun provideCacheToDataMapper() = FavouriteCacheToDataMapper()

    @Provides
    fun provideDataToCacheMapper() = FavouriteDataToCacheMapper()

    @Singleton
    @Provides
    fun provideCurrencyDao(database: AppDatabase) = database.currencyDao
}