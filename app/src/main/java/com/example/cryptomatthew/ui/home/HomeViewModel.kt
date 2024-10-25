package com.example.cryptomatthew.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomatthew.data.local.dao.CurrencyDao
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.network.NetworkRepository
import com.example.cryptomatthew.models.Currencies
import com.example.cryptomatthew.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dao: CurrencyDao) : ViewModel() {
    private val networkRepository = NetworkRepository()

    private val _currencies = MutableStateFlow<Currencies>(emptyList())
    val currencies: StateFlow<Currencies> = _currencies.asStateFlow()


    init {
       networkRepository.fetchTickersList(
           onResponse = { code, currencies ->
               if (code == 200 && currencies != null)
                   _currencies.update { currencies }
           },
           onFailure = { t -> Log.d("HomeViewModel", "Error while fetching tickers: ${t.message}")}

       )
    }



    fun saveCurrencies(currencies: List<CurrencyEntity>) {
        viewModelScope.launch {
            for (x in currencies)
                dao.insert(x)
            Log.d("HomeViewModel", "inserted currency")

        }
    }

    fun getCurrencies() {
        viewModelScope.launch {
            dao.getCurrencies().let {
                val currencies = it.map { Currency(it) }
                _currencies.update { currencies }
                Log.d("HomeViewModel", it.toString())

            }

        }
    }
}