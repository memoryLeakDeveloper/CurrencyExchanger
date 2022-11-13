package com.currency.exchanger.core.cache

import android.content.Context
import androidx.room.*
import com.currency.exchanger.data.favourite.cache.FavouriteDao
import com.currency.exchanger.data.favourite.cache.FavouriteTable

@Database(
    version = 1,
    entities = [
        FavouriteTable::class,
//        NotificationTable::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val currencyDao: FavouriteDao
//    abstract val notificationDao: NotificationDao

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