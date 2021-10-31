package com.example.stackoverflowclone.data.remote

import com.example.stackoverflowclone.data.remote.dto.QuestionListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mehul Bisht on 31-10-2021
 */

interface StackOverflowApi {

    @GET(".")
    suspend fun getQuestion(
        @Query("key") key: String,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String,
    ): QuestionListDto
}