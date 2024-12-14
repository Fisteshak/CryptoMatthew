package com.example.cryptomatthew.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes() {
    Home,
    Favorites,
    Scanner,
    CurrencyInfo,

}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Монеты", Routes.Home, Icons.Default.Home),
    TopLevelRoute("Избранное", Routes.Favorites, Icons.Default.Favorite),
    TopLevelRoute("QR-сканер", Routes.Scanner, Icons.Default.Search),
)