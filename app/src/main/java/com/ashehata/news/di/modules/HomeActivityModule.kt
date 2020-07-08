package com.ashehata.news.di.modules

import com.ashehata.news.dataSource.RemoteData
import com.ashehata.news.home.HomeAdapter
import com.ashehata.news.home.HomeRepository
import com.ashehata.news.home.HomeUseCase
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class HomeActivityModule {

    @Provides
    fun provideUseCase(homeRepository: HomeRepository) = HomeUseCase(homeRepository)

    @Provides
    fun provideRepo(remoteData: RemoteData) = HomeRepository(remoteData)

    @Provides
    fun provideAdapter(loadImage: RequestManager) = HomeAdapter(loadImage)
}