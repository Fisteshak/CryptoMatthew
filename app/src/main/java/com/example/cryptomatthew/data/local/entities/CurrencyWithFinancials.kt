package com.example.cryptomatthew.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyWithFinancials(
    @Embedded val currency: CurrencyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "currencyId"
    )
    val financials: List<FinancialsEntity>


)