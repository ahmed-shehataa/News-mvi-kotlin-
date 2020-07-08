package com.ashehata.news.home

sealed class HomeIntent {
    object RequestNews : HomeIntent()
    object RequestRefresh : HomeIntent()
    // Use data class if u wanna pass data from view to viewmodel
}