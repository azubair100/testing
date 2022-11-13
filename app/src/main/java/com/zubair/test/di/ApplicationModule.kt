package com.zubair.test.di

import com.zubair.test.network.BookApi
import com.zubair.test.network.BookRepository
import com.zubair.test.network.NetworkBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideNetworkService(): BookApi = NetworkBuilder.create()

    @Provides
    @Singleton
    fun providePhotoRepository(api: BookApi): BookRepository = BookRepository(api)

}