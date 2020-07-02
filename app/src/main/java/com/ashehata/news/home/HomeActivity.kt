package com.ashehata.news.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ashehata.news.R
import com.ashehata.news.base.BaseActivity
import com.ashehata.news.externals.ErrorType
import com.ashehata.news.externals.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpRv()
        setRefreshing()
        getNews()
        updateUi()
    }

    private fun setRefreshing() {
        pb_refreshing.setOnRefreshListener {
            viewModel.serRefreshing()
        }
    }

    private fun getNews() {
        viewModel.getData()
    }

    private fun setUpRv() {
        rv_articles.setHasFixedSize(true)
        adapter = HomeAdapter()
        rv_articles.adapter = adapter
    }

    private fun updateUi() {
        viewModel.viewState.observe(this, Observer {

            /**
             * Show / Hide progress refreshing & loading
             */
            pb_loading.visibility = if (it?.isLoading!!) View.VISIBLE else View.GONE
            pb_refreshing.isRefreshing = it.isRefreshing!!

            if (it.data != null) {
                // Pass the data
                adapter.submitList(it.data)
                //rv_articles.adapter = adapter
            }

            val message = when(it?.error) {
                is ErrorType.NoConnection -> getString(R.string.no_internet)
                is ErrorType.Error -> (it.error as ErrorType.Error).message
                null -> ""
            }
            if (!message.isEmpty()) {
                parent_view.showMessage(message)
            }

        })
    }



}