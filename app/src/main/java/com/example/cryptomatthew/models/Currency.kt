package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity

data class Currency(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    var finsUSD: Financials? = null,
    var finsRUB: Financials? = null,
    var isFavorite: Boolean
) {

    constructor(currencyEntity: CurrencyEntity, finsUSD: FinancialsEntity? = null, finsRUB: FinancialsEntity? = null) : this(
        currencyEntity.id,
        currencyEntity.rank,
        currencyEntity.name,
        currencyEntity.symbol,
        isFavorite = currencyEntity.isFavorite
    ) {
        if (finsUSD != null) this.finsUSD = Financials(finsUSD, "$")
        if (finsRUB != null) this.finsRUB = Financials(finsRUB, "₽")
    }


}


