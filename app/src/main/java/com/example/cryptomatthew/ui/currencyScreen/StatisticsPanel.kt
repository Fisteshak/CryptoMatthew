package com.example.cryptomatthew.ui.currencyScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.R
import com.example.cryptomatthew.models.Change
import com.example.cryptomatthew.models.Financials

@Composable
fun StatisticsPanel(financials: Financials, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column {
            Text(
                "Статистика",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                StatisticsElement(
                    "Цена",
                    financials.price.formatShort() ?: stringResource(R.string.No_data),
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                StatisticsElement(
                    "Капитализация",
                    financials.marketCap.formatShort() ?: stringResource(R.string.No_data),
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                StatisticsElement(
                    "Обьем 24 ч.",
                    financials.volume24h.formatShort() ?: stringResource(R.string.No_data),
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                ChangeElement(
                    "Изменение обьема 24 ч.",
                    financials.volume24hChange24h,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                ChangeElement(
                    "Изменение цены 1 г.",
                    financials.percentChange1y,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )

                ChangeElement(
                    "Изменение цены 1 мес.",
                    financials.percentChange30d,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {

                ChangeElement(
                    "Изменение цены 7 д.",
                    financials.percentChange7d,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )

                ChangeElement(
                    "Изменение цены 24 ч.",
                    financials.percentChange24h,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {

                ChangeElement(
                    "Изменение цены 12 ч.",
                    financials.percentChange12h,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )

                ChangeElement(
                    "Изменение цены 1 ч.",
                    financials.percentChange1h,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {

                StatisticsElement(
                    "Макс. цена",
                    financials.athPrice.formatShort() ?: stringResource(R.string.No_data),
                    modifier = Modifier.fillMaxWidth(0.5f)
                )

                ChangeElement(
                    "Изменение цены с макс.",
                    financials.percentFromPriceAth,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {

                StatisticsElement(
                    "Дата макс. цены",
                    financials.athDate?.toString() ?: stringResource(R.string.No_data),
                    modifier = Modifier.fillMaxWidth(0.5f)
                )

            }


        }
    }
}

@Composable
fun ChangeArrow(change: Change, modifier: Modifier = Modifier) {
    if (change.value != null) {
        Box(
            modifier = modifier
        ) {
            ChangeArrow(change.value)
        }

    }
}

@Composable
fun ChangeArrow(dir: Double) {
    val color = when {
        dir < 0.0 -> Color.Red
        dir == 0.0 -> Color.Gray
        else -> Color.Green
    }
    Spacer(modifier = Modifier
        .size(10.dp)
        .drawBehind {
            if (dir != 0.0) {

                val path = androidx.compose.ui.graphics
                    .Path()
                    .apply {
                        val triangleSide = size.width
                        val height = triangleSide / 2f
                        val offset = height / 2f
                        if (dir > 0) {
                            moveTo(triangleSide / 2f, offset)
                            lineTo(0f, height + offset)
                            lineTo(triangleSide, height + offset)
                        } else {
                            moveTo(triangleSide / 2f, height + offset)
                            lineTo(0f, offset)
                            lineTo(triangleSide, offset)
                        }
                        close()
                    }
                drawPath(path, color)
            } else {
                val height = 2f
                val width = size.width
                val margin = width / 8
                val offset = (width - height) / 2
                drawRect(
                    color,
                    topLeft = Offset(margin, offset),
                    size = Size(width - 2 * margin, height)
                )
            }

        }
    )
}



@Composable
fun ChangeElement(header: String, change: Change, modifier: Modifier = Modifier) {
    Column(modifier.padding(2.dp)) {
        Text(header, style = MaterialTheme.typography.bodySmall)
        Row {
            ChangeArrow(change,
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
            )
            Text(change.formatSignless() ?: stringResource(R.string.No_data), style = MaterialTheme.typography.bodyMedium)

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
@Preview
@Composable
fun ChangeArrowPreview() {
    Row {
        ChangeArrow(1.0)
        ChangeArrow(0.0)
        ChangeArrow(-1.0)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ChangeElementPreview() {
    ChangeElement(
        "Изменение объема",
        Change(24.5),
        )
}

