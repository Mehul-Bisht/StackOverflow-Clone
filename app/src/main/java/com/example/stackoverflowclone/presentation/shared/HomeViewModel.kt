package com.example.stackoverflowclone.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflowclone.dispatcher.CoroutineDispatcherProvider
import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.domain.use_cases.*
import com.example.stackoverflowclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuestionListUseCase: GetQuestionListUseCase,
    private val getUniqueTagsUseCase: GetUniqueTagsUseCase,
    private val updateQueryUseCase: UpdateQueryUseCase,
    private val getQuestionsFilteredByTitleOrUsernameUseCase: GetQuestionsFilteredByTitleOrUsernameUseCase,
    private val updateTagUseCase: UpdateTagUseCase,
    private val getQuestionByTagUseCase: GetQuestionByTagUseCase,
    private val updateAvgDataUseCase: UpdateAvgDataUseCase,
    private val getAvgMetricsUseCase: GetAvgMetricsUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _homeState: MutableStateFlow<QuestionsResult> =
        MutableStateFlow(QuestionsResult.Loading)
    val homeState: StateFlow<QuestionsResult> get() = _homeState

    private val _avgState: MutableStateFlow<AvgMetricsState> =
        MutableStateFlow(AvgMetricsState.Initial)
    val avgState: StateFlow<AvgMetricsState> get() = _avgState

    private val _tagList: MutableStateFlow<List<String>> = MutableStateFlow(listOf())

    private val _eventChannel = Channel<List<String>>()
    val event = _eventChannel.receiveAsFlow()

    sealed class QuestionsResult {
        object Loading : QuestionsResult()
        data class Success(val data: List<Question>?) : QuestionsResult()
        data class Error(val message: String?) : QuestionsResult()
    }

    sealed class AvgMetricsState {
        object Initial : AvgMetricsState()
        data class Success(val data: Pair<Double, Double>) : AvgMetricsState()
        object Other : AvgMetricsState()
    }

    init {
        getHomeFeedFlow()
        getQuestionsFilteredByTitleOrUsername()
        getQuestionsFilteredByTags()
        getAvg()
    }

    private fun getAvg() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getAvgMetricsUseCase().collect { metrics ->
                when {
                    metrics.first == null || metrics.second == null -> {
                        _avgState.value = AvgMetricsState.Other
                    }
                    else -> {
                        _avgState.value = AvgMetricsState.Success(
                            Pair(
                                metrics.first!!,
                                metrics.second!!
                            )
                        )
                    }
                }
            }
        }
    }

    fun getFilters() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            _eventChannel.send(_tagList.value)
        }
    }

    fun setQuery(text: String) {
        updateQueryUseCase(text)
    }

    fun setTag(text: String?) {
        updateTagUseCase(text)
    }

    fun getHomeFeedFlow() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            getQuestionListUseCase().collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        _homeState.value = QuestionsResult.Loading
                    }
                    is Resource.Success -> {
                        _homeState.value = QuestionsResult.Success(state.data)
                        state.data?.let {
                            updateAvgDataUseCase(it)
                        }
                        getUniqueTags()
                    }
                    is Resource.Error -> {
                        _homeState.value = QuestionsResult.Error(state.message)
                    }
                }
            }
        }
    }

    private fun getUniqueTags() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getUniqueTagsUseCase().collect {
                _tagList.value = it
            }
        }
    }

    private fun getQuestionsFilteredByTitleOrUsername() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getQuestionsFilteredByTitleOrUsernameUseCase().collectLatest {
                _homeState.value = QuestionsResult.Success(it)
                updateAvgDataUseCase(it)
            }
        }
    }

    private fun getQuestionsFilteredByTags() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getQuestionByTagUseCase().collectLatest {
                _homeState.value = QuestionsResult.Success(it)
                updateAvgDataUseCase(it)
            }
        }
    }
}