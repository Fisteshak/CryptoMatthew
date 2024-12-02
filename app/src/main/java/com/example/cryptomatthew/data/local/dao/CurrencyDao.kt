package com.example.cryptomatthew.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.network.models.NetworkTicker
import com.example.cryptomatthew.data.utils.tickerToCurrencyAndFinancialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {


    @Query("SELECT * FROM currency ORDER BY rank ASC")
    fun getAllCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency ORDER BY rank ASC")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM financials")
    fun getAllFinancialsFlow(): Flow<List<FinancialsEntity>>


    @Query("SELECT * FROM tick WHERE currencyId = :currencyId ORDER BY timestampSeconds ASC")
    suspend fun getCurrencyTicks(currencyId: String): List<TickEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyTicks(ticks: List<TickEntity>)

    @Transaction
    suspend fun insertCurrencyData(ticker: NetworkTicker) {

    }

    @Transaction
    suspend fun insertCurrenciesData(tickers: List<Pair<NetworkTicker, Boolean>>) {
        for (ticker in tickers) {
            val (currencyEntity, financialsEntityUSD, financialsEntityRUB) =
                tickerToCurrencyAndFinancialsEntity(ticker.first)

            currencyEntity.isFavorite = ticker.second

            insertCurrencyEntity(currencyEntity)
            insertFinancialsEntity(financialsEntityUSD)
            insertFinancialsEntity(financialsEntityRUB)
        }
    }

    @Transaction
    suspend fun clearDB() {
        deleteAllCurrencyEntities()
        deleteAllFinancialEntities()
    }

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrencyEntities()

    @Query("DELETE FROM financials")
    suspend fun deleteAllFinancialEntities()

    @Query("UPDATE currency SET isFavorite = :isFavorite WHERE id = :currencyId")
    suspend fun updateCurrencyIsFavorite(currencyId: String, isFavorite: Boolean)
}