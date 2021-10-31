package com.example.stackoverflowclone.dependencies

import com.example.stackoverflowclone.BuildConfig.BASE_URL
import com.example.stackoverflowclone.data.remote.StackOverflowApi
import com.example.stackoverflowclone.data.repository.StackOverflowRepositoryImpl
import com.example.stackoverflowclone.dispatcher.CoroutineDispatcherProvider
import com.example.stackoverflowclone.domain.repository.StackOverflowRepository
import com.example.stackoverflowclone.domain.use_cases.*
import com.example.stackoverflowclone.presentation.shared.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Mehul Bisht on 31-10-2021
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideStackOverflowApi(retrofit: Retrofit): StackOverflowApi {
        return retrofit.create(StackOverflowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStackOverflowRepository(api: StackOverflowApi): StackOverflowRepository {
        return StackOverflowRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeViewModelFactory(
        getQuestionListUseCase: GetQuestionListUseCase,
        getUniqueTagsUseCase: GetUniqueTagsUseCase,
        updateQueryUseCase: UpdateQueryUseCase,
        getQuestionsFilteredByTitleOrUsernameUseCase: GetQuestionsFilteredByTitleOrUsernameUseCase,
        updateTagUseCase: UpdateTagUseCase,
        getQuestionByTag: GetQuestionByTagUseCase,
        updateAvgDataUseCase: UpdateAvgDataUseCase,
        getAvgMetricsUseCase: GetAvgMetricsUseCase,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            getQuestionListUseCase,
            getUniqueTagsUseCase,
            updateQueryUseCase,
            getQuestionsFilteredByTitleOrUsernameUseCase,
            updateTagUseCase,
            getQuestionByTag,
            updateAvgDataUseCase,
            getAvgMetricsUseCase,
            coroutineDispatcherProvider
        )
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}