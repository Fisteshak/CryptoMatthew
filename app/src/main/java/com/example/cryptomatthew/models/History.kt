package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.utils.parseLongSecondsToLocalDate
import kotlinx.datetime.LocalDate

data class History(
    val currencyId: String,
    val ticks: List<Tick>,
)

// TODO store price, volume and marketCap in Price class
data class Tick(
    val price: Price,
    val timestamp: LocalDate,
    val volume24h: Price,
    val marketCap: Price,
) {
    constructor(tickEntity: TickEntity, currencySymbol: String) : this(
        Price(tickEntity.price, currencySymbol),
        parseLongSecondsToLocalDate(tickEntity.timestampSeconds),
        Price(tickEntity.volume24h, currencySymbol),
        Price(tickEntity.marketCap, currencySymbol),
    )
}

