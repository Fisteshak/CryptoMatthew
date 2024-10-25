package com.example.cryptomatthew.models

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity

data class Tick(
    val price: Double,
    val timestamp: String,
    val volume24h: Double,
    val marketCap: Double,
)
