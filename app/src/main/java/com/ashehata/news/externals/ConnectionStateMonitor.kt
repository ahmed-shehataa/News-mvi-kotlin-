package com.ashehata.news.externals

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log


class ConnectionStateMonitor(
    context: Application
) : NetworkCallback() {

    private var connectivityManager: ConnectivityManager? = null
    private var onInternetAvailable: OnInternetAvailable? = null

    private val networkRequest = lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }.value


    init {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        register()
    }

    fun getNetworkState(onInternetAvailable: OnInternetAvailable){
        this.onInternetAvailable = onInternetAvailable
    }

    fun register() {
        connectivityManager?.registerNetworkCallback(networkRequest, this)
    }

    fun unregister() {
        connectivityManager?.unregisterNetworkCallback(this)
    }

    // Likewise, you can have a disable method that simply calls ConnectivityManager.unregisterNetworkCallback(NetworkCallback) too.
    override fun onAvailable(network: Network) {
        // Do what you need to do here
        // Notify the activity for internet connection
        onInternetAvailable?.onNetworkAvailable()
        //Log.v("onAvailable", network.toString())
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        onInternetAvailable?.onNetworkDisconnect()
    }

    interface OnInternetAvailable {
        fun onNetworkAvailable()
        fun onNetworkDisconnect()
    }

}