package com.ashehata.news.externals

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext


class ConnectionStateMonitor(
    private val context: Application
) : NetworkCallback() {

    private var connectivityManager: ConnectivityManager? = null
    private var onInternetAvailable: OnInternetAvailable? = null
    //private lateinit var action: () -> Unit

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

    fun setOnAvailable(onInternetAvailable: OnInternetAvailable){
        this.onInternetAvailable = onInternetAvailable
    }

    fun register() {
        connectivityManager?.registerNetworkCallback(networkRequest, this)
    }


    /*
    fun onConnected(yourAction: () -> Unit) {
        //action = yourAction
    }

     */

    // Likewise, you can have a disable method that simply calls ConnectivityManager.unregisterNetworkCallback(NetworkCallback) too.
    override fun onAvailable(network: Network) {
        // Do what you need to do here
        //action()
        // Notify the activity for internet connection
        onInternetAvailable?.onAvailable()
        Log.v("onAvailable", network.toString())
    }

    fun unregister() {
        connectivityManager?.unregisterNetworkCallback(this)
    }

    interface OnInternetAvailable {
        fun onAvailable()
    }

}