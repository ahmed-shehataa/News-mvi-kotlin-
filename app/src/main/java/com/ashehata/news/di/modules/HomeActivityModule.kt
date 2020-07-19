package com.ashehata.news.di.modules

import com.ashehata.news.dataSource.RemoteData
import com.ashehata.news.home.HomeAdapter
import com.ashehata.news.home.HomeRepository
import com.ashehata.news.home.HomeUseCase
import com.ashehata.news.home.SourceAdapter
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class HomeActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun provideUseCase(homeRepository: HomeRepository) = HomeUseCase(homeRepository)

    @Provides
    @ActivityRetainedScoped
    fun provideRepo(remoteData: RemoteData) = HomeRepository(remoteData)

    @Provides
    @ActivityRetainedScoped
    fun provideArticlesAdapter(loadImage: RequestManager) = HomeAdapter(loadImage)

    @Provides
    @ActivityRetainedScoped
    fun provideSourcesAdapter() = SourceAdapter()
}