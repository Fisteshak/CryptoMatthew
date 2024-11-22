package com.example.cryptomatthew.data.utils

import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.local.entities.FinancialsEntity
import com.example.cryptomatthew.data.network.models.NetworkTicker
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun tickerToCurrencyAndFinancialsEntity(ticker: NetworkTicker): Triple<CurrencyEntity, FinancialsEntity, FinancialsEntity>  {
    val currencyEntity = CurrencyEntity(ticker)
    val financialsEntityUSD = FinancialsEntity(ticker.quotes.USD, ticker.id, "USD")
    val financialsEntityRUB = FinancialsEntity(ticker.quotes.RUB, ticker.id, "RUB")
    return Triple(currencyEntity, financialsEntityUSD, financialsEntityRUB)
}

fun parseRFC3339ToLongSeconds(dateString: String): Long {
    val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(dateString, formatter)
    val instant = zonedDateTime.toInstant()
    return instant.epochSecond
}

fun parseLongSecondsToLocalDate(timestampSeconds: Long): LocalDate {
    return Instant.fromEpochSeconds(timestampSeconds)
        .toLocalDateTime(timeZone = TimeZone.UTC).date //TODO ??
}