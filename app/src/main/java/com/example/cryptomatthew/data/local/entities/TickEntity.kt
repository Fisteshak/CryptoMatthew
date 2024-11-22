package com.example.cryptomatthew.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.cryptomatthew.data.network.models.NetworkTick
import com.example.cryptomatthew.data.utils.parseRFC3339ToLongSeconds

@Entity(tableName = "tick", primaryKeys = ["currencyId", "timestampSeconds"])
data class TickEntity(
    val currencyId: String,
    @ColumnInfo(defaultValue = "0")
    val timestampSeconds: Long,
    val price: Double,
    val volume24h: Double,
    val marketCap: Double,
) {
    constructor(currencyId: String, networkTick: NetworkTick) : this(
        currencyId,
        parseRFC3339ToLongSeconds(networkTick.timestamp),
        networkTick.price,
        networkTick.volume24h,
        networkTick.marketCap
    ) {

    }
}
