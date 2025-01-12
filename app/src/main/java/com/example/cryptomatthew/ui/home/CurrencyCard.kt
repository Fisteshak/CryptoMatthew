package com.example.cryptomatthew.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cryptomatthew.R
import com.example.cryptomatthew.models.Currency

@Composable
fun CryptoCard(
    currency: Currency,
    onCurrencyClick: (currency: Currency) -> Unit,
    onFavoriteIconClick: (currencyId: String) -> Unit,
    onNotificationsEnabledIconClick: (currencyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(vertical = 4.dp)
            .clickable(onClick = {
                onCurrencyClick(currency)
            }),
        verticalAlignment = Alignment.CenterVertically,

    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end = 2.dp)
                .width(35.dp)
        ) {

            Text(
                text = currency.rank.toString(),

                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
            )
        }
        Text(
            text = currency.symbol,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 6.dp)
        )
        Spacer(modifier = Modifier
            .width(20.dp)
            .weight(1f))


        Text(
            text = currency.finsUSD?.price?.formatLong() ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
        )

        Box(
            modifier = Modifier
                .requiredWidth(40.dp)
                .padding(start = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            //TODO change content description
            Image(
                painter = painterResource(
                    id = if (currency.isFavorite) R.drawable.favorite_filled else R.drawable.favorite_empty
                ),
                contentDescription = "is favorite",
                modifier = Modifier.clickable { onFavoriteIconClick(currency.id) }
            )
        }

        Box(

            modifier = Modifier
                .requiredWidth(40.dp)
                .padding(start = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            //TODO change content description
            Image(
                painter = painterResource(
                    id = if (currency.rateNotificationsEnabled) R.drawable.bell else R.drawable.bell_outline
                ),
                contentDescription = "rate notifications enabled",
                modifier = Modifier.clickable { onNotificationsEnabledIconClick(currency.id) }
            )
        }
    }
}


