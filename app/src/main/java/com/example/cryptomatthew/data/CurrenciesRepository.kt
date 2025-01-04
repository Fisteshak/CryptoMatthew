package com.example.cryptomatthew.data

import android.util.Log
import com.example.cryptomatthew.data.local.OfflineCurrenciesRepository
import com.example.cryptomatthew.data.local.entities.TickEntity
import com.example.cryptomatthew.data.network.NetworkCurrenciesRepository
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.models.History
import com.example.cryptomatthew.models.Tick
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(
    private val networkCurrenciesRepository: NetworkCurrenciesRepository,
    private val offlineCurrenciesRepository: OfflineCurrenciesRepository
) {


    fun getCurrencies(): Flow<List<Currency>> {
        return offlineCurrenciesRepository.currenciesFlow.filter { it.isNotEmpty() && it[0].finsUSD != null }
    }


    private suspend fun clearDB() {
        offlineCurrenciesRepository.clearDB()
    }


    //TODO this synchronization is ok (but not ideal) in terms of speed,
    // but i really don't like that i need to pass Pair<Ticker, Boolean> to insert function
    // that will work for now, but i should fix this in future
    // maybe some intermediate class between NetworkTicker and CurrencyEntity (or add isFavorite to NetworkTicker?)
    suspend fun updateCurrencies() {
        networkCurrenciesRepository.latestTickers.collect {
            val tickers = it.body()
            Log.d("Currencies Repo", "tickers request response: ${it.code()}")

            if (it.code() == 200 && tickers != null) {

                val current = offlineCurrenciesRepository.getCurrencies()


                val new = tickers.map { newTicker ->
                    Pair(newTicker, current.find { it.id == newTicker.id }?.isFavorite ?: false)
                }

                clearDB()

                offlineCurrenciesRepository.insertCurrenciesData(new)


            } else {
                Log.d("Currencies Repo", "error while updating tickers: ${it.code()}, ${it.message()}")
            }
        }
    }


    // retrieves history from network and stores in DB
    suspend fun updateCurrencyHistory(currencyId: String, startDate: String) {
        val response = networkCurrenciesRepository.getWeeklyTickerHistory(currencyId, startDate)
        if (response != null) {
            Log.d("NetworkRepository", "history for $currencyId: ${response.body()}")
        }
        // TODO handle null case
        if (response == null) return;
        val ticks = response.body()
        if (response.code() == 200 && ticks != null) {
            val history = ticks.map { TickEntity(currencyId, it) }

            offlineCurrenciesRepository.insertCurrencyHistory(history)
        }

    }

    // gets history from DB
    suspend fun getCurrencyHistory(currencyId: String): History {
        val ticks = offlineCurrenciesRepository.getCurrencyHistory(currencyId)

        return History(
            currencyId,
            ticks.map {Tick(it, "$")}
        )

    }

    suspend fun setIsFavorite(currencyId: String, isFavorite: Boolean) {
        offlineCurrenciesRepository.updateCurrencyIsFavorite(currencyId, isFavorite)
    }

}