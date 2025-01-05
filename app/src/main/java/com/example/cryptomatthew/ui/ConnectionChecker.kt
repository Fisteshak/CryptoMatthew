package com.example.cryptomatthew.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService


import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ConnectionChecker @Inject constructor(@ApplicationContext context: Context) {

    var hasConnection: Boolean = false
        private set

    private var onAvailableCallback = {}
    private var onLostCallback = {}

    // registers callbacks and calls corresponding callback depending on state of network connection
    fun registerCallbacks(onAvailable: (() -> Unit)? = null, onLost: (() -> Unit)? = null) {
        if (onAvailable != null) onAvailableCallback = onAvailable
        if (onLost != null) onLostCallback = onLost

        if (hasConnection) onAvailableCallback() else onLostCallback()
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            hasConnection = true
            onAvailableCallback()
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {

            super.onCapabilitiesChanged(network, networkCapabilities)

        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            hasConnection = false
            onLostCallback()
        }
    }

    init {
        val connectivityManager = getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager

        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities == null) hasConnection = false;
        else {
            if (!capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) hasConnection = false;

        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }





}