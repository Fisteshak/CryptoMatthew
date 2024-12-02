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
    version = 6,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 1, to = 2,
            spec = AppDatabase.AutoMigration1To2::class
        ),
        AutoMigration (
            from = 2, to = 3,
            spec = AppDatabase.AutoMigration2To3::class
        ),
        AutoMigration (
            from = 3, to = 4,
            spec = AppDatabase.AutoMigration3To4::class
        ),
        AutoMigration (
            from = 4, to = 5,
            spec = AppDatabase.AutoMigration4To5::class
        ),
        AutoMigration(
            from = 5, to = 6,
            spec = AppDatabase.AutoMigration5To6::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {
    @DeleteColumn("tick", "timestamp")
    class AutoMigration1To2 : AutoMigrationSpec

    class AutoMigration2To3 : AutoMigrationSpec

    class AutoMigration3To4 : AutoMigrationSpec

    @DeleteColumn("financials", "athDate")
    class AutoMigration4To5 : AutoMigrationSpec

    class AutoMigration5To6 : AutoMigrationSpec


    abstract fun currencyDao(): CurrencyDao

}