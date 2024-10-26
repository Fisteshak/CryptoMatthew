package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.Quote

data class Financials(
    val price: Double,
    val volume24h: Double? = null,
    val volume24hChange24h: Double? = null,
    val marketCap: Double? = null,
    val marketCapChange24h: Double? = null,
    val percentChange1h: Double? = null,
    val percentChange12h: Double? = null,
    val percentChange24h: Double? = null,
    val percentChange7d: Double? = null,
    val percentChange30d: Double? = null,
    val percentChange1y: Double? = null,
) {
    constructor(q: Quote) : this(
        q.price,
        q.volume_24h,
        q.volume_24h_change_24h,
        q.market_cap,
        q.market_cap_change_24h,
        q.percent_change_1h,
        q.percent_change_12h,
        q.percent_change_24h,
        q.percent_change_7d,
        q.percent_change_30d,
        q.percent_change_1y
    )
    constructor(f: FinancialsEntity) : this(
        f.price,
        f.volume24h,
        f.volume24hChange24h,
        f.marketCap,
        f.marketCapChange24h,
        f.percentChange1h,
        f.percentChange12h,
        f.percentChange24h,
        f.percentChange7d,
        f.percentChange30d,
        f.percentChange1y
    )
}
