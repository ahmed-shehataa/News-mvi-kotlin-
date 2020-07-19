package com.ashehata.news.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashehata.news.R
import com.ashehata.news.base.BaseActivity
import com.ashehata.news.externals.ErrorType
import com.ashehata.news.externals.showMessage
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var articlesAdapter: HomeAdapter
    @Inject
    lateinit var sourceAdapter: SourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpRv()
        updateUi()
        setRefreshing()
        if(savedInstanceState == null) {
            sendDataIntent()
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

               val message = when(it.error) {
                   is ErrorType.NoConnection -> getString(R.string.no_internet)
                   is ErrorType.Error -> (it.error as ErrorType.Error).message
                   ErrorType.NoError -> getString(R.string.success_message)
                   else -> ""
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
        rv_sources.setHasFixedSize(true)
        rv_articles.adapter = articlesAdapter
        rv_sources.adapter = sourceAdapter
    }


    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        //viewModel.intentChannel.offer(HomeIntent.RequestRefresh)
    }
}