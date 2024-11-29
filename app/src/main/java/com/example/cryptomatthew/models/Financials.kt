package com.example.cryptomatthew.models

import android.util.Log
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import java.util.Locale
import kotlin.math.abs

data class Financials(
    val price: Price,
    val volume24h: Price,
    val volume24hChange24h: Change,
    val marketCap: Price,
    val marketCapChange24h: Change,
    val percentChange1h: Change,
    val percentChange12h: Change,
    val percentChange24h: Change,
    val percentChange7d: Change,
    val percentChange30d: Change,
    val percentChange1y: Change,
) {
    constructor(f: FinancialsEntity, currencySymbol: String) : this(
        Price(f.price, currencySymbol),
        Price(f.volume24h, currencySymbol),
        Change(f.volume24hChange24h),
        Price(f.marketCap, currencySymbol),
        Change(f.marketCapChange24h),
        Change(f.percentChange1h),
        Change(f.percentChange12h),
        Change(f.percentChange24h),
        Change(f.percentChange7d),
        Change(f.percentChange30d),
        Change(f.percentChange1y)
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
            "K" to 1_000.0,

        )
        for ((suffix, threshold) in units) {
            if (value >= threshold) {
                Log.d("PriceFormatShort", "$currencySymbol ${value / threshold} $suffix" )
                return String.format(Locale.FRANCE, "%s%.2f %s", currencySymbol, value / threshold, suffix)
            }
        }
        return String.format(Locale.FRANCE, "%s%.4f", currencySymbol, value)
    }

    fun formatLong(): String {
        if (value == null) return "Нет данных"

        return String.format(Locale.FRANCE, "%s%,.2f", currencySymbol, value)
    }

}

data class Change(val value: Double?) {
    fun formatFull(): String {
        if (value == null) return "Нет данных"
        else return String.format(Locale.FRANCE, "%.1f%%", value )
    }
    
    fun formatSignless(): String {
        if (value == null) return "Нет данных"
        else return String.format(Locale.FRANCE, "%.1f%%", abs(value))
    }
    
    
}