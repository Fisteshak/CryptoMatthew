package com.example.cryptomatthew.ui.currencyScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.example.cryptomatthew.models.Tick
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlinx.datetime.LocalDate


@Composable
fun HistoryPlot(history: List<Tick>, modifier: Modifier = Modifier) {
    val first = history.first().timestamp
    Log.d("HistoryPlot", "historysize: ${history.size}")
    val lines = history.mapIndexed {index, it ->
        Log.d("HistoryPlot", "index: $index")
        DataPoint(LocalConfiguration.current.screenWidthDp.toFloat() * (index.toFloat() / history.size) / 20, it.price.toFloat())
    }
    Log.d("plotdata", lines.toString())
    LineGraph(
        modifier = modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .height(300.dp)
        ,
        plot = LinePlot(
            lines = listOf(
                LinePlot.Line(
                    dataPoints = lines,
                    LinePlot.Connection(),
                    LinePlot.Intersection(),
                    LinePlot.Highlight(),
                )
            ),
            grid = LinePlot.Grid(
                color = Color.Gray
            ),
            xAxis = LinePlot.XAxis(
                steps = history.size,
                //stepSize = ( / history.size).dp
            ) {

                min, offset, max ->
                for (i in history) {
                    Text("${i.timestamp.dayOfMonth}-${i.timestamp.month.value}")
                }
            },
            isZoomAllowed = false,


        )
    )
}

@Composable
fun HistoryPlot2(history: List<Tick>, modifier: Modifier = Modifier) {
    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "",
            data = history.map { it.price },
            lineColor = Color.Gray,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
        ),

    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        linesParameters = lineParameters,
        isGrid = true,
        gridColor = Color.Blue,
        xAxisData =
        history.mapIndexed { index, elem ->
            if (index % 100 == 0) {
                "${elem.timestamp.dayOfMonth}.${elem.timestamp.month.value}.${elem.timestamp.year}"
            } else {
                ""
            }
        },
        animateChart = false,
        showGridWithSpacer = false,
        yAxisStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray,
        ),
        xAxisStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.W400
        ),
        yAxisRange = 8,
        oneLineChart = false,
        gridOrientation = GridOrientation.HORIZONTAL,
        chartRatio = 2f
    )
}

@Composable
fun HistoryPlotPlaceholder(history: List<Tick>?, text: String = ":((", modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentAlignment = Alignment.Center
    ) {
        if (history == null) {
            Text("Loading...")
        } else {
            Text(text)
        }
    }
}



@Preview(widthDp = 360, backgroundColor = 0xFFFFFF)
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
    HistoryPlotPlaceholder(history)
}