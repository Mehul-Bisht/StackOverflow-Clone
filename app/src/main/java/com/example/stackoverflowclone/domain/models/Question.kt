package com.example.stackoverflowclone.domain.models

/**
 * Created by Mehul Bisht on 31-10-2021
 */

data class Question(
    val answer_count: Int,
    val creation_date: Int,
    val is_answered: Boolean,
    val last_activity_date: Int,
    val last_edit_date: Int,
    val link: String,
    val owner: Owner,
    val question_id: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val view_count: Int
)
