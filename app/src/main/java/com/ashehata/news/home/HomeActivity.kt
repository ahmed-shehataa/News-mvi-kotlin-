package com.ashehata.news.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashehata.news.R
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }
}