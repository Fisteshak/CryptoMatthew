package com.example.cryptomatthew.ui.currencyScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Tick
import com.example.cryptomatthew.ui.currencyScreen.lineGraph.CurrencyLineGraph
import com.example.cryptomatthew.ui.currencyScreen.lineGraph.DataPoint
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlin.math.min


@Composable
fun HistoryPlot(history: List<Tick>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentAlignment = Alignment.Center
    ) {
        val formatter = LocalDate.Format {
            dayOfMonth(); char('.'); monthNumber(); chars("."); yearTwoDigits(2000)
        };
        if (history.size < 2) {
            HistoryPlotPlaceholder("Недостаточно данных для построения графика")
        } else {
            CurrencyLineGraph(
                dataPoints = history.map {
                    DataPoint(
                        it.price.value ?: 0.0,
                        it.timestamp.format(formatter)
                    )
                },
                min(6, history.size),
                4
            )
        }

    }
}

@Composable
fun HistoryPlotPlaceholder(text: String, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentAlignment = Alignment.Center
    ) {

        Text(text)

    }
}
