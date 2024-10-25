package com.example.cryptomatthew.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.CurrencyWithFinancials

@Dao
interface CurrencyDao {
    @Transaction
    @Query("SELECT * FROM currency")
    fun getCurrenciesWithFinancials(): List<CurrencyWithFinancials>

    @Insert
    suspend fun insert(currencyEntity: CurrencyEntity)

}