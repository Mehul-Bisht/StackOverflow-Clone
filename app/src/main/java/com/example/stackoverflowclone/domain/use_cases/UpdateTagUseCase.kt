package com.example.stackoverflowclone.domain.use_cases

import com.example.stackoverflowclone.domain.repository.StackOverflowRepository
import javax.inject.Inject

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class UpdateTagUseCase @Inject constructor(
    private val repository: StackOverflowRepository
) {

    operator fun invoke(query: String?) {
        repository.setTag(query)
    }
}