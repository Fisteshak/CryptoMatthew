package com.example.cryptomatthew

import android.content.Context
import androidx.room.Room
import com.example.cryptomatthew.data.local.AppDatabase
import com.example.cryptomatthew.data.local.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Volatile
    private var Instance: AppDatabase? = null

    @Provides
    @Singleton
    fun currencyDatabase(@ApplicationContext context: Context): AppDatabase {
        // if the Instance is not null, return it, otherwise create a new database instance.
        return Instance ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, "currency_database")
                .build()
                .also { Instance = it }
        }
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
        return database.currencyDao()
    }

}
