package com.example.cryptomatthew.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.CurrencyWithFinancials

@Dao
interface CurrencyDao {
    @Transaction
    @Query("SELECT * FROM currency")
    fun getCurrenciesWithFinancials(): List<CurrencyWithFinancials>

    @Query("SELECT * FROM currency")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyEntity: CurrencyEntity)

}