package com.example.cryptomatthew.models

import android.util.Log
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import java.util.Locale

data class Financials(
    val price: Price,
    val volume24h: Price,
    val volume24hChange24h: Double? = null,
    val marketCap: Price,
    val marketCapChange24h: Double? = null,
    val percentChange1h: Double? = null,
    val percentChange12h: Double? = null,
    val percentChange24h: Double? = null,
    val percentChange7d: Double? = null,
    val percentChange30d: Double? = null,
    val percentChange1y: Double? = null,
) {
    constructor(f: FinancialsEntity, currencySymbol: String) : this(
        Price(f.price, currencySymbol),
        Price(f.volume24h, currencySymbol),
        f.volume24hChange24h,
        Price(f.marketCap, currencySymbol),
        f.marketCapChange24h,
        f.percentChange1h,
        f.percentChange12h,
        f.percentChange24h,
        f.percentChange7d,
        f.percentChange30d,
        f.percentChange1y
    )
}

data class Price(
    val value: Double?,
    val currencySymbol: String
) {
    fun formatShort(): String {

        if (value == null) return "Нет данных"

        val units = listOf(
            "T" to 1_000_000_000_000.0,
            "B" to 1_000_000_000.0,
            "M" to 1_000_000.0,
            "K" to 1_000.0
        )
        for ((suffix, threshold) in units) {
            if (value >= threshold) {
                Log.d("PriceFormatShort", "$currencySymbol ${value / threshold} $suffix" )
                return String.format(Locale.FRANCE, "%s%.2f %s", currencySymbol, value / threshold, suffix)
            }
        }
        return value.toString()
    }

    fun formatLong(): String {
        if (value == null) return "Нет данных"

        return String.format(Locale.FRANCE, "%s%,.2f", currencySymbol, value)
    }

}