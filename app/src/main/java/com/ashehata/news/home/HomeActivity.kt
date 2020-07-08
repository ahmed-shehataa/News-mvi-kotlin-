package com.ashehata.news.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ashehata.news.R
import com.ashehata.news.base.BaseActivity
import com.ashehata.news.externals.ErrorType
import com.ashehata.news.externals.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpRv()
        sendDataIntent()
        updateUi()
        setRefreshing()

    }

    private fun sendDataIntent() {
        viewModel.intentChannel.offer(HomeIntent.RequestNews)
    }

    private fun updateUi() {
       lifecycleScope.launch {
           viewModel.stateChannel.collect {

               pb_loading.visibility = if (it?.isLoading!!) View.VISIBLE else View.GONE
               pb_refreshing.isRefreshing = it.isRefreshing!!

               if (it.data != null) {
                   // Pass the data
                   adapter.submitList(it.data)
                   //rv_articles.adapter = adapter
               }

               val message = when(it.error) {
                   is ErrorType.NoConnection -> getString(R.string.no_internet)
                   is ErrorType.Error -> (it.error as ErrorType.Error).message
                   ErrorType.NoError -> "Done"
                   null -> ""
               }

               if (!message.isEmpty()) {
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
        rv_articles.adapter = adapter
    }


    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        /*
        runOnUiThread {
            // To simulate something
            pb_refreshing.isRefreshing = true
            viewModel.intentChannel.offer(HomeIntent.RequestRefresh)
        }

         */

    }
}