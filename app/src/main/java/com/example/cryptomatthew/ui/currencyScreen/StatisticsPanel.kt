package com.example.cryptomatthew.ui.currencyScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Financials

@Composable
fun StatisticsPanel(financials: Financials, finSymbol: String, modifier: Modifier = Modifier) {
    Box() {
        Column {
            Text("Статистика", modifier = modifier.align(Alignment.CenterHorizontally))
            Row() {
                StatisticsElement("Цена", financials.price.formatShort(), modifier = Modifier.fillMaxWidth(0.5f))
                StatisticsElement("Капитализация", financials.marketCap.formatShort(), modifier = Modifier.fillMaxWidth(1f))
            }
            Row {
                StatisticsElement("Обьем 24 ч.", financials.volume24h.formatShort(), modifier = Modifier.fillMaxWidth(0.5f))
                StatisticsElement(
                    "Изменение обьема 24 ч.",
                    "$finSymbol${financials.volume24hChange24h}",
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }


        }
    }
}



@Composable
fun StatisticsElement(header: String, text: String, modifier: Modifier = Modifier) {
    Column(modifier.padding(2.dp)) {
        Text(header, style = MaterialTheme.typography.bodySmall)
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}