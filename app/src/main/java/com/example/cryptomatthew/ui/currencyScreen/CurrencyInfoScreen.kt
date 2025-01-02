package com.example.cryptomatthew.ui.currencyScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.R
import com.example.cryptomatthew.models.Currency
import com.example.cryptomatthew.models.History


@Composable
fun CurrencyInfoScreen(currency: Currency, history: History?, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(6.dp)) {


            Text(currency.name, style = MaterialTheme.typography.titleLarge)
            Text(currency.symbol, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Row {
                Text(currency.finsUSD?.price?.formatLong() ?: stringResource(R.string.No_data), style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                Text(currency.finsRUB?.price?.formatLong() ?: stringResource(R.string.No_data), style = MaterialTheme.typography.titleLarge)
            }
            Log.d("CurrencyInfoScreen", history.toString())

            if (history == null) {
                HistoryPlotPlaceholder(
                    "Загрузка...",
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                )
            } else if (history.ticks.isEmpty()) {
                HistoryPlotPlaceholder(
                    "Ошибка при получении истории",
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                )
            } else {
                HistoryPlot(
                    history.ticks,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }



            StatisticsPanel(currency.finsUSD!!)


        }

    }
}