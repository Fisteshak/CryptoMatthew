package com.example.cryptomatthew.models

import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.utils.parseLongSecondsToLocalDate
import kotlinx.datetime.LocalDate
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
    val athPrice: Price,
    val athDate: LocalDate?,
    val percentFromPriceAth: Change
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
        Change(f.percentChange1y),
        Price(f.athPrice, currencySymbol),
        parseLongSecondsToLocalDate(f.athDate!!), //TODO should probably handle null case
        Change(f.percentFromPriceAth)
    )
}

data class Price(
    val value: Double?,
    val currencySymbol: String
) {

    /**
     * Formats a numeric value into a shortened string representation with appropriate suffixes.
     *
     * This function converts large numeric values into a more readable format by appending
     * suffixes like "K" for thousands, "M" for millions, "B" for billions, and "T" for trillions.
     * It includes the currency symbol and formats the number using ' ' as separator for every 3 numbers and ',' as separator for fractional part
     *
     * @return A formatted string representing the value with a suffix, or `null` if the value is null.
     */
    fun formatShort(): String? {

        if (value == null) return null

        val units = listOf(
            "T" to 1_000_000_000_000.0,
            "B" to 1_000_000_000.0,
            "M" to 1_000_000.0,
            "K" to 1_000.0,

        )
        for ((suffix, threshold) in units) {
            if (value >= threshold) {
                return String.format(Locale.FRANCE, "%s%.2f %s", currencySymbol, value / threshold, suffix)
            }
        }
        return String.format(Locale.FRANCE, "%s%.4f", currencySymbol, value)
    }

    fun formatLong(): String? {
        if (value == null) return null
        return String.format(Locale.FRANCE, "%s%,.2f", currencySymbol, value)
    }

}

data class Change(val value: Double?) {
    fun formatFull(): String? {
        if (value == null) return null;
        return String.format(Locale.FRANCE, "%.1f%%", value)
    }
    
    fun formatSignless(): String? {
        if (value == null) return null;
        return String.format(Locale.FRANCE, "%.1f%%", abs(value))
    }
    
    
}