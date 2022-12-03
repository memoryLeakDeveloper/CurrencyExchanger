package com.currency.exchanger.core.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currency.exchanger.data.favourite.cache.FavouriteDao
import com.currency.exchanger.data.favourite.cache.FavouriteTable

@Database(
    version = 1,
    entities = [
        FavouriteTable::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val currencyDao: FavouriteDao

    companion object {
        @Volatile
        private lateinit var instanse: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            synchronized(this) {
                instanse = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "database"
                ).build()
            }
            return instanse
        }
    }
}