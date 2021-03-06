package com.example.stackoverflowclone.data.remote.dto

data class QuestionDto(
    val answer_count: Int,
    val content_license: String,
    val creation_date: Int,
    val is_answered: Boolean,
    val last_activity_date: Int,
    val last_edit_date: Int,
    val link: String,
    val owner: OwnerDto,
    val question_id: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val view_count: Int
)