package com.example.stackoverflowclone.data.repository

import com.example.stackoverflowclone.data.remote.StackOverflowApi
import com.example.stackoverflowclone.data.remote.dto_mapper.toQuestion
import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.domain.repository.StackOverflowRepository
import com.example.stackoverflowclone.utils.Constant
import com.example.stackoverflowclone.utils.Constant.NO_TAG_FOUND
import com.example.stackoverflowclone.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class StackOverflowRepositoryImpl @Inject constructor(
    private val api: StackOverflowApi
) : StackOverflowRepository {

    private val _data: MutableStateFlow<List<Question>> = MutableStateFlow(listOf())

    private val _query = MutableStateFlow("")

    private val _tag = MutableStateFlow("")

    private val _avgData: MutableStateFlow<List<Question>> = MutableStateFlow(listOf())

    @FlowPreview
    @ExperimentalCoroutinesApi
    val queryFlow = _query
        .debounce(Constant.QUERY_DEBOUNCE_MILLIS)
        .flatMapLatest {
            flow {
                emit(it)
            }
        }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val tagFlow = _tag
        .flatMapLatest {
            flow {
                emit(it)
            }
        }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val avgFlow = _avgData
        .flatMapLatest {
            flow {
                emit(it)
            }
        }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override suspend fun getQuestionsFilteredByTag(): Flow<List<Question>> = flow {
        tagFlow.collect { tag ->

            val tagMatched = _data.value.filter {
                it.tags.contains(tag)
            }

            if (tagMatched.isNotEmpty() && tag != NO_TAG_FOUND) {
                emit(tagMatched)
            } else {
                emit(_data.value)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override suspend fun getQuestionsFilteredByTitleOrUsername(): Flow<List<Question>> = flow {
        queryFlow.collect { query ->

            val match: HashSet<Question> = hashSetOf()

            val titleMatched = _data.value.filter { question ->
                question.title.toLowerCase().contains(query.toLowerCase())
            }
            titleMatched.forEach {
                match.add(it)
            }

            val userMatched = _data.value.filter { question ->
                question.owner.display_name.toLowerCase().contains(query.toLowerCase())
            }
            userMatched.forEach {
                match.add(it)
            }

            emit(match.toList())
        }
    }

    override suspend fun getQuestions(): Flow<Resource<List<Question>>> = flow {
        try {
            emit(Resource.Loading<List<Question>>())
            val result = api.getQuestion(
                key = "ZiXCZbWaOwnDgpVT9Hx8IA((",
                order = "desc",
                sort = "activity",
                site = "stackoverflow"
            ).items.map { it.toQuestion() }
            setData(result)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error<List<Question>>(e.message.toString()))
        }
    }

    override suspend fun getUniqueTags(): Flow<List<String>> = flow {
        val tags: HashSet<String> = hashSetOf()
        _data.value.forEach { question ->
            question.tags.forEach { tag ->
                tags.add(tag)
            }
        }
        emit(tags.toList())
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override suspend fun getAvgData(): Flow<Pair<Double?, Double?>> = flow {
        avgFlow.collect { questions ->
            val avgViewCount = questions.map { it.view_count }.average()
            val avgAnswerCount = questions.map { it.answer_count }.average()

            emit(Pair(avgViewCount, avgAnswerCount))
        }
    }

    override fun setQuery(query: String) {
        _query.value = query
    }

    override fun setTag(tag: String?) {
        _tag.value = tag ?: NO_TAG_FOUND
    }

    override fun setAvgData(data: List<Question>) {
        _avgData.value = data
    }

    private fun setData(data: List<Question>) {
        _data.value = data
    }
}