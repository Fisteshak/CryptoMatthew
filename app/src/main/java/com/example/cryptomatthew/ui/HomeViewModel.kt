package com.example.cryptomatthew.ui

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomatthew.data.CurrenciesRepository
import com.example.cryptomatthew.data.SettingsDataStore
import com.example.cryptomatthew.data.network.ConnectionChecker
import com.example.cryptomatthew.data.notifications.NotificationChannelId
import com.example.cryptomatthew.data.notifications.NotificationScheduler
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.models.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val connectionChecker: ConnectionChecker,
    private val notificationScheduler: NotificationScheduler,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {


    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())
    val currencies: StateFlow<List<Currency>> = _currencies.asStateFlow()

    private val _histories = MutableStateFlow<MutableList<History>>(mutableListOf())
    val histories: StateFlow<List<History>> = _histories.asStateFlow()

    var recompose by mutableStateOf(false)

    var isShowingNoConnectionBar: Boolean by mutableStateOf(false)
        private set


    private fun showNoConnectionBar() {
        isShowingNoConnectionBar = true

    }

    fun hideNoConnectionBar() {
        isShowingNoConnectionBar = false
    }

    init {
        Log.d("ViewModel", "created view model")
        runDataNetworkUpdateCoroutine()
        runDataUpdateFlow()

        connectionChecker.registerCallbacks(
            onLost = { showNoConnectionBar() },
            onAvailable = { hideNoConnectionBar() }
        )
    }

    fun updateCurrencyHistory(currencyId: String) {
        viewModelScope.launch {
            Log.d("ViewModel", "starting to update history for $currencyId")

            with(Dispatchers.IO) {

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val date = LocalDateTime.now().minusYears(1).plusDays(2).format(formatter);

                currenciesRepository.updateCurrencyHistory(currencyId, date)
                val history = _histories.value.find { it.currencyId == currencyId }
                val newHistory = currenciesRepository.getCurrencyHistory(currencyId)

                if (history == newHistory) return@launch

                if (history == null) {
                    _histories.update {
                        it.add(newHistory)
                        it
                    }
                    Log.d("ViewModel", "added history for $currencyId: ${newHistory}")
                } else {

                    _histories.update {
                        it[_histories.value.indexOf(history)] = newHistory
                        it
                    }
                    Log.d("ViewModel", "updated history for $currencyId: ${newHistory}")
                }
                recompose = !recompose // TODO described in CurrencyInfoScreen


            }
        }
    }

    private fun runDataNetworkUpdateCoroutine() {
        viewModelScope.launch {
            with(Dispatchers.IO) {
                currenciesRepository.updateCurrencies()

            }
        }
    }

    private fun runDataUpdateFlow() {
        viewModelScope.launch {
            currenciesRepository.getCurrenciesFlow().collect { x ->
                _currencies.update { x }
                Log.d("HomeViewModel", "updated tickers ${x.toString()}")
            }

        }
    }

    fun toggleFavorite(currencyId: String) {

        viewModelScope.launch {
            val x = _currencies.value.find { it.id == currencyId }
            if (x != null) {
                //_currencies.update { it.forEach { x -> x.isFavorite = true }; it}
                //x.isFavorite = !x.isFavorite
                currenciesRepository.setIsFavorite(currencyId, !x.isFavorite)
            } else {
                Log.d(
                    "HomeViewModel",
                    "setIsFavorite: can't find currency with id = $currencyId"
                )
            }
        }
    }

    fun toggleNotificationsEnabled(currencyId: String) {

        viewModelScope.launch {
            val x = _currencies.value.find { it.id == currencyId }
            if (x != null) {
                //_currencies.update { it.forEach { x -> x.isFavorite = true }; it}
                //x.isFavorite = !x.isFavorite
                currenciesRepository.setRateNotificationsEnabled(
                    currencyId,
                    !x.rateNotificationsEnabled
                )
            } else {
                Log.d(
                    "HomeViewModel",
                    "setNotificationsEnabled: can't find currency with id = $currencyId"
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun addNotificationScheduleTime(time: TimePickerState) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, time.hour)
        calendar.set(Calendar.MINUTE, time.minute)
        calendar.set(Calendar.SECOND, 0)

        viewModelScope.launch {
            var h = time.hour.toString()
            if (h.length == 1) h = "0" + h
            var m = time.minute.toString()
            if (m.length == 1) m = "0" + m

            with(Dispatchers.IO) {
                settingsDataStore.saveNotificationsTime("$h:$m")
            }
        }

        notificationScheduler.scheduleRepeatingNotification(
            calendar,
            24 * 60 * 60 * 1000,
            2,
            NotificationChannelId.EXCHANGE_RATES
        )
    }

    fun onNotificationEnabledChange(enabled: Boolean) {
        if (!enabled) notificationScheduler.cancelNotification(2)

        viewModelScope.launch {

            with(Dispatchers.IO) {
                settingsDataStore.saveNotificationsEnabled(enabled)

                // get time from data store
                val time = notificationsTime.first()
                val hour = time.substring(0..1).toInt()
                val min = time.substring(3..4).toInt()

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, min)
                calendar.set(Calendar.SECOND, 0)

                notificationScheduler.scheduleRepeatingNotification(
                    calendar,
                    24 * 60 * 60 * 1000,
                    2,
                    NotificationChannelId.EXCHANGE_RATES
                )
            }
        }
    }

    val notificationEnabled: Flow<Boolean> = settingsDataStore.getNotificationsEnabled()


    val notificationsTime: Flow<String> = settingsDataStore.getNotificationsTime()

}