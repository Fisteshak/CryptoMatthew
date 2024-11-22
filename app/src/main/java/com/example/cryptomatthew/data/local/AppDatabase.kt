package com.example.cryptomatthew.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.local.entities.TickEntity


@Database(
    entities = [CurrencyEntity::class, FinancialsEntity::class, TickEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 1, to = 2,
            spec = AppDatabase.MyAutoMigration::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {
    @DeleteColumn("tick", "timestamp")
    class MyAutoMigration : AutoMigrationSpec

    abstract fun currencyDao(): CurrencyDao

}