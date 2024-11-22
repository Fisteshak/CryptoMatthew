package com.example.cryptomatthew.ui.currencyScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptomatthew.models.Tick
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlinx.datetime.LocalDate

@Composable
fun HistoryPlot(history: List<Tick>) {
    val first = history.first().timestamp
    val lines = history.map {
        DataPoint(it.timestamp.toEpochDays().toFloat(), it.price.toFloat())
    }
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = lines,
                    LinePlot.Connection(),
                    LinePlot.Intersection(),
                    LinePlot.Highlight(),
                )
            ),
            xAxis = LinePlot.XAxis(steps = history.size) {
                min, offset, max ->
                for (i in history) {
                    Text("${i.timestamp.dayOfMonth}-${i.timestamp.month.value}")
                }
            }

        )
    )
}

@Preview(widthDp = 260)
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