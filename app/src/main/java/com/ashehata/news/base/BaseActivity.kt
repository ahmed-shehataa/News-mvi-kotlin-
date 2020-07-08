package com.ashehata.news.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashehata.news.externals.ConnectionStateMonitor
import javax.inject.Inject

//@AndroidEntryPoint
open class BaseActivity : AppCompatActivity(), ConnectionStateMonitor.OnInternetAvailable {

    @Inject
    lateinit var connectionStateMonitor: ConnectionStateMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionStateMonitor.getNetworkState(this)
    }

    override fun onResume() {
        super.onResume()
        // Register/Listen network state
        connectionStateMonitor.register()
    }

    override fun onPause() {
        super.onPause()
        // Unregister network state
        connectionStateMonitor.unregister()
    }

    override fun onStop() {
        super.onStop()
        // Unregister network state
        //connectionStateMonitor.unregister()
    }

    override fun onNetworkAvailable() {
        runOnUiThread {
            Toast.makeText(this, "Internet on", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNetworkDisconnect() {
        runOnUiThread {
            Toast.makeText(this, "Internet off", Toast.LENGTH_SHORT).show()
        }
    }
}