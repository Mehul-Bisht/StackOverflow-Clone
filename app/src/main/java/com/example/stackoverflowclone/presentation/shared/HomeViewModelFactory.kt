package com.example.stackoverflowclone.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackoverflowclone.dispatcher.CoroutineDispatcherProvider
import com.example.stackoverflowclone.domain.use_cases.*
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class HomeViewModelFactory @Inject constructor(
    private val getQuestionListUseCase: GetQuestionListUseCase,
    private val getUniqueTagsUseCase: GetUniqueTagsUseCase,
    private val updateQueryUseCase: UpdateQueryUseCase,
    private val getQuestionsFilteredByTitleOrUsernameUseCase: GetQuestionsFilteredByTitleOrUsernameUseCase,
    private val updateTagUseCase: UpdateTagUseCase,
    private val getQuestionByTagUseCase: GetQuestionByTagUseCase,
    private val updateAvgDataUseCase: UpdateAvgDataUseCase,
    private val getAvgMetricsUseCase: GetAvgMetricsUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                getQuestionListUseCase,
                getUniqueTagsUseCase,
                updateQueryUseCase,
                getQuestionsFilteredByTitleOrUsernameUseCase,
                updateTagUseCase,
                getQuestionByTagUseCase,
                updateAvgDataUseCase,
                getAvgMetricsUseCase,
                coroutineDispatcherProvider
            ) as T
        }
        throw IllegalArgumentException("ViewModel class not supported")
    }
}