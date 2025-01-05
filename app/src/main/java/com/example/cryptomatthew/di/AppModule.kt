package com.example.cryptomatthew.di

import android.content.Context
import androidx.room.Room
import com.example.cryptomatthew.data.CurrenciesRepository
import com.example.cryptomatthew.data.local.AppDatabase
import com.example.cryptomatthew.data.local.OfflineCurrenciesRepository
import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.network.CoinpaprikaAPI
import com.example.cryptomatthew.data.network.NetworkCurrenciesRepository
import com.example.cryptomatthew.ui.ConnectionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
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

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {
    private const val baseURL = "https://api.coinpaprika.com/v1/"

    private val instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val apiService: CoinpaprikaAPI = instance.create(CoinpaprikaAPI::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return instance
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(): CoinpaprikaAPI {
        return apiService
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CurrencyRepositoryModule {
    @Provides
    @Singleton
    fun provideCurrenciesRepository(
        offlineCurrenciesRepository: OfflineCurrenciesRepository,
        networkCurrenciesRepository: NetworkCurrenciesRepository
    ): CurrenciesRepository {
        return CurrenciesRepository(networkCurrenciesRepository, offlineCurrenciesRepository)
    }

    @Provides
    @Singleton
    fun provideOfflineCurrenciesRepository(dao: CurrencyDao): OfflineCurrenciesRepository {
        return OfflineCurrenciesRepository(dao)
    }

    @Provides
    @Singleton
    fun provideNetworkCurrenciesRepository(api: CoinpaprikaAPI) : NetworkCurrenciesRepository {
        return NetworkCurrenciesRepository(api)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object ConnectionCheckerModule {
    private var instance: ConnectionChecker? = null

    @Provides
    @Singleton
    fun connectionChecker(@ApplicationContext context: Context): ConnectionChecker {
        // if the Instance is not null, return it, otherwise create a new database instance.

        return instance ?: synchronized(this) {
            ConnectionChecker(context)
                .also { instance = it }
        }

    }
}
