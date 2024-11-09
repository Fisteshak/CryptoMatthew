package com.example.cryptomatthew.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.Ticker
import com.example.cryptomatthew.data.utils.tickerToCurrencyAndFinancialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {


    @Query("SELECT * FROM currency ORDER BY rank ASC")
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM financials")
    fun getAllFinancials(): Flow<List<FinancialsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity)

    @Transaction
    suspend fun insertCurrencyData(ticker: Ticker) {

    }

    @Transaction
    suspend fun insertCurrenciesData(tickers: List<Ticker>) {
        for (ticker in tickers) {
            val (currencyEntity, financialsEntityUSD, financialsEntityRUB) =
                tickerToCurrencyAndFinancialsEntity(ticker)

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


}