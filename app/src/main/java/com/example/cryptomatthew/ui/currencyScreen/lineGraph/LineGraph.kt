package com.example.cryptomatthew.ui.currencyScreen.lineGraph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import java.util.Locale

@Composable
fun LineGraph(
    dataPoints: List<Double>,
    modifier: Modifier = Modifier
) {
    val minValue = dataPoints.minOrNull() ?: 0.0
    val maxValue = dataPoints.maxOrNull() ?: 1.0
    val padding = (maxValue - minValue) * 0.1
    val adjustedMin = minValue - padding
    val adjustedMax = maxValue + padding
    val range = adjustedMax - adjustedMin

    Canvas(modifier = modifier.fillMaxSize()) {
        val xStep = size.width / (dataPoints.size - 1).coerceAtLeast(1)
        val path = Path()

        dataPoints.forEachIndexed { index, value ->
            val x = xStep * index
            val y = size.height - ((value - adjustedMin) / range * size.height).toFloat()
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
    }
}




class DataPoint(
    val value: Double,
    val label: String
)

@Composable
fun CurrencyLineGraph(
    dataPoints: List<DataPoint>,
    xValuesNum: Int,
    yValuesNum: Int,
    modifier: Modifier = Modifier
) {
    val minValue = dataPoints.minOfOrNull { it.value } ?: 0.0
    val maxValue = dataPoints.maxOfOrNull { it.value } ?: 1.0
    val padding = (maxValue - minValue) * 0.1
    val adjustedMin = minValue - padding
    val adjustedMax = maxValue + padding
    val range = adjustedMax - adjustedMin

    val yLabelPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 30f
        color = android.graphics.Color.BLACK
    }

    val xLabelPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 30f
        color = android.graphics.Color.BLACK
        textAlign = android.graphics.Paint.Align.CENTER
    }

    val gridColor = Color(0x70444444)

    Canvas(modifier = modifier.fillMaxSize()) {

        // Y-axis labels
        val yStep = (range - 2*padding) / (yValuesNum-1)
        drawIntoCanvas {
            for (i in 0..<yValuesNum) {
                val value = minValue + i * yStep
                val yPos = size.height - ((value - adjustedMin) / range * size.height).toFloat()

                var valueDiff = maxValue - minValue;
                var i = 0;
                while (valueDiff < 1f) {
                    i++
                    valueDiff *= 10
                }



                it.nativeCanvas.drawText(
                    String.format(
                        Locale.FRENCH, "%.${i+2}f", value),
                    10f,
                    yPos-4,
                    yLabelPaint
                )
                // horizontal grid
                drawLine(
                    color = gridColor,
                    start = Offset(0f, yPos),
                    end = Offset(size.width, yPos),
                    strokeWidth = 1f
                )
            }
        }


        // X-axis labels
        val xStepPx = size.width / (xValuesNum - 1).coerceAtLeast(1)
        val xStepData = (dataPoints.size - 1) / (xValuesNum - 1).coerceAtLeast(1)
        drawIntoCanvas {
            for (i in 1 until xValuesNum-1) {
                val index = i * xStepData
                val xPos = i * xStepPx
                it.nativeCanvas.drawText(
                    dataPoints[index].label,
                    xPos,
                    size.height - 18f,
                    xLabelPaint
                )
                // vertical grid

                val lowerBorder = size.height - ((minValue - adjustedMin) / range * size.height).toFloat();
                drawLine(
                    color = gridColor,
                    start = Offset(xPos, 0f),
                    end = Offset(xPos, lowerBorder),
                    strokeWidth = 2f
                )
            }



        }

        // Line graph
        val path = Path()
        dataPoints.forEachIndexed { index, value ->
            val x = size.width * index / (dataPoints.size - 1).coerceAtLeast(1)
            val y = size.height - ((value.value - adjustedMin) / range * size.height).toFloat()
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
    }
}