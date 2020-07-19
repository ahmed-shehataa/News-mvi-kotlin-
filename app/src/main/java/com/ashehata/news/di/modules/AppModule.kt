package com.ashehata.news.di.modules

import android.app.Application
import com.ashehata.news.externals.ConnectionStateMonitor
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application) = Glide.with(application)

    @Singleton
    @Provides
    fun provideConnectivity(context: Application) = ConnectionStateMonitor(context)

}