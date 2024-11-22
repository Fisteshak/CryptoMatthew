package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.utils.parseLongSecondsToLocalDate
import kotlinx.datetime.LocalDate

data class Tick(
    val price: Double,
    val timestamp: LocalDate,
    val volume24h: Double,
    val marketCap: Double,
) {
    constructor(tickEntity: TickEntity) : this(
        tickEntity.price,
        parseLongSecondsToLocalDate(tickEntity.timestampSeconds),
        tickEntity.volume24h,
        tickEntity.marketCap
    )
}

