package com.example.cryptomatthew.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.CurrencyWithFinancials
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Transaction
    @Query("SELECT * FROM currency")
    fun getCurrenciesWithFinancials(): List<CurrencyWithFinancials>

    @Query("SELECT * FROM currency ORDER BY rank ASC")
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM financials")
    fun getAllFinancials(): Flow<List<FinancialsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyEntity(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialsEntity(financialsEntity: FinancialsEntity)

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrencyEntities()

    @Query("DELETE FROM financials")
    suspend fun deleteAllFinancialEntities()


}