package com.example.stackoverflowclone.domain.use_cases

import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.domain.repository.StackOverflowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class GetQuestionsFilteredByTitleOrUsernameUseCase @Inject constructor(
    private val repository: StackOverflowRepository
) {
    operator fun invoke(): Flow<List<Question>> = flow {
        repository.getQuestionsFilteredByTitleOrUsername().collect {
            if (it.isNotEmpty()) {
                emit(it)
            }
        }
    }
}