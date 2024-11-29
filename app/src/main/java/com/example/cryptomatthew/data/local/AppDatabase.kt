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
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 1, to = 2,
            spec = AppDatabase.AutoMigration1To2::class
        ),
        AutoMigration (
            from = 2, to = 3,
            spec = AppDatabase.AutoMigration2To3::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {
    @DeleteColumn("tick", "timestamp")
    class AutoMigration1To2 : AutoMigrationSpec

    class AutoMigration2To3 : AutoMigrationSpec

    abstract fun currencyDao(): CurrencyDao

}