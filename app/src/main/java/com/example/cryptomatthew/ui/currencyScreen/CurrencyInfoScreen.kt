package com.example.cryptomatthew.ui.currencyScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Currency

@Composable
fun CurrencyInfoScreen(currency: Currency) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {

            Row {
                Text(currency.name)
                Spacer(modifier = Modifier.weight(1f))
                Text(currency.finsUSD!!.price.toString())
            }
            if (currency.history != null) {
                Log.d("CurrencyInfoScreen: ", "history for ${currency.id}: ${currency.history}")
                for (x in currency.history!!) {
                    Row(modifier = Modifier.padding(all = 4.dp)) {
                        Text(x.timestamp)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(x.price.toString())
                    }
                }
            }
            else {
                Log.d("CurrencyInfoScreen: ", "history for ${currency.id}: null")

                Text("Loading")
            }

        }

    }
}