package com.ashehata.news.home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashehata.news.R
import com.ashehata.news.base.BaseActivity
import com.ashehata.news.externals.*
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_16
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var articlesAdapter: HomeAdapter

    @Inject
    lateinit var sourceAdapter: SourceAdapter

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpRv()
        updateUi()
        setRefreshing()
        if (savedInstanceState == null) {
            sendDataIntent()
        }
        createPDF()
    }

    private fun createPDF() {
        btn_pdf.setOnClickListener {
            if (allPermissionsGranted()) {
                bytesToPDF("Hello world!")
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }


    private fun sendDataIntent() {
        lifecycleScope.launchWhenStarted {
            viewModel.intentChannel.send(HomeIntent.RequestNews)
        }
    }

    private fun updateUi() {
        lifecycleScope.launch {
            viewModel.stateChannel.collect {

                pb_loading.visibility = if (it?.isLoading!!) View.VISIBLE else View.GONE
                pb_refreshing.isRefreshing = it.isRefreshing!!

                if (it.listArticles != null) {
                    // Pass the data
                    articlesAdapter.submitList(it.listArticles)
                }

                if (it.listSources != null) {
                    // Pass the data
                    sourceAdapter.submitList(it.listSources)
                }

                val message: String = when (it.error) {
                    ErrorEntity.Network -> getString(R.string.no_internet)
                    is ErrorEntity.NotFound -> (it.error as ErrorEntity.NotFound).message
                    ErrorEntity.Unavailable -> "Unavailable"
                    ErrorEntity.Unknown -> "Unknown error"
                    ErrorEntity.AccessDenied -> "Access Denied"
                    null -> ""
                }

                if (message.isNotEmpty()) {
                    parent_view.showMessage(message)
                }
            }
        }

    }


    private fun setRefreshing() {
        pb_refreshing.setOnRefreshListener {
            viewModel.intentChannel.offer(HomeIntent.RequestRefresh)
        }
    }

    private fun setUpRv() {
        rv_articles.setHasFixedSize(true)
        rv_sources.setHasFixedSize(true)
        rv_articles.adapter = articlesAdapter
        rv_sources.adapter = sourceAdapter
    }


    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        //viewModel.intentChannel.offer(HomeIntent.RequestRefresh)
    }
}