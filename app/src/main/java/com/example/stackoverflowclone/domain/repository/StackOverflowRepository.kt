package com.example.stackoverflowclone.domain.repository

import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mehul Bisht on 31-10-2021
 */

interface StackOverflowRepository {

    suspend fun getQuestions(): Flow<Resource<List<Question>>>

    suspend fun getUniqueTags(): Flow<List<String>>

    suspend fun getQuestionsFilteredByTitleOrUsername(): Flow<List<Question>>

    suspend fun getQuestionsFilteredByTag(): Flow<List<Question>>

    suspend fun getAvgData(): Flow<Pair<Double?, Double?>>

    fun setQuery(query: String)

    fun setTag(tag: String?)

    fun setAvgData(data: List<Question>)
}