package com.example.cryptomatthew.ui.currencyScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.models.Tick
import com.example.cryptomatthew.ui.currencyScreen.lineGraph.CurrencyLineGraph
import com.example.cryptomatthew.ui.currencyScreen.lineGraph.DataPoint
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char


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
        CurrencyLineGraph(
            dataPoints = history.map { DataPoint(it.price, it.timestamp.format(formatter)) },
            6,
            4
            )

    }
}

@Composable
fun HistoryPlotPlaceholder(text: String, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentAlignment = Alignment.Center
    ) {

        Text(text)

    }
}



@Preview(widthDp = 360, heightDp = 200, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewHistoryPlot() {
    val history = listOf(
        Tick(100.0, LocalDate(2024, 1, 1), 0.0, 0.0),
        Tick(120.0, LocalDate(2024, 2, 2), 0.0, 0.0),
        Tick(110.0, LocalDate(2024, 3, 3), 0.0, 0.0),
        Tick(150.0, LocalDate(2024, 4, 4), 0.0, 0.0),
        Tick(165.0, LocalDate(2024, 5, 5), 0.0, 0.0),
        Tick(114.0, LocalDate(2024, 6, 6), 0.0, 0.0),
        Tick(80.0 , LocalDate(2024, 7, 7), 0.0, 0.0),
    )
    HistoryPlot(history)
}