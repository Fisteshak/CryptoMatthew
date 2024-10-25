package com.example.cryptomatthew.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.cryptomatthew.DBApplication
import com.example.cryptomatthew.data.local.AppDatabase
import com.example.cryptomatthew.data.local.entities.CurrencyEntity
import com.example.cryptomatthew.data.network.NetworkRepository
import com.example.cryptomatthew.models.Currencies
import com.example.cryptomatthew.models.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : ViewModel() {
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
        //dao.insert(Currency())
    }

    fun getDatabase(context: Context) {

    }

//    suspend fun insertCurrency(currency: Currency) {
//        viewModelScope.launch {
//            dao.insert(CurrencyEntity("btc", 1, "bitcoin", "BTC", null, null))
//           // Log.d("HomeViewModel", dao.getCurrenciesWithFinancials().toString())
//
//        }
//    }
}