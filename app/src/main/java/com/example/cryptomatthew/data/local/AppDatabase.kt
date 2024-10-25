package com.example.cryptomatthew.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.local.entities.TickEntity


@Database(entities = [CurrencyEntity::class, FinancialsEntity::class, TickEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}