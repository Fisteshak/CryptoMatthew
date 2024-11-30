package com.example.cryptomatthew.data.local.entities

import androidx.room.Entity
import com.example.cryptomatthew.data.network.models.NetworkQuote
import com.example.cryptomatthew.data.utils.parseRFC3339ToLongSeconds

@Entity(tableName = "financials", primaryKeys = ["currencyId", "priceCurrency"])
data class FinancialsEntity(
    val currencyId: String,        // example: btc-bitcoin
    val priceCurrency: String,     // example: USD
    val price: Double,
    val volume24h: Double,
    val volume24hChange24h: Double? = null,
    val marketCap: Double? = null,
    val marketCapChange24h: Double? = null,
    val percentChange1h: Double? = null,
    val percentChange12h: Double? = null,
    val percentChange24h: Double? = null,
    val percentChange7d: Double? = null,
    val percentChange30d: Double? = null,
    val percentChange1y: Double? = null,
    val athPrice: Double? = null,          //ath = all time high
    val athDate: Long? = null,
    val percentFromPriceAth: Double? = null
) {
    constructor(q: NetworkQuote, currencyId: String, priceCurrency: String) : this(
        currencyId,
        priceCurrency,
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
        q.percent_change_1y,
        q.ath_price,
        parseRFC3339ToLongSeconds(q.ath_date),
        q.percent_from_price_ath
    )
}