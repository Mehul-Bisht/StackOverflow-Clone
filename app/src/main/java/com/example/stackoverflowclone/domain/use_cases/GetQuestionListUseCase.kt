package com.example.stackoverflowclone.domain.use_cases

import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.domain.repository.StackOverflowRepository
import com.example.stackoverflowclone.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class GetQuestionListUseCase @Inject constructor(
    private val repository: StackOverflowRepository
) {

    operator fun invoke(): Flow<Resource<List<Question>>> = flow {
        repository.getQuestions().collect { state ->
            emit(state)
        }
    }
}