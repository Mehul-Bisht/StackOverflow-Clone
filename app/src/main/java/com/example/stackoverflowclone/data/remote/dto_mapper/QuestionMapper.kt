package com.example.stackoverflowclone.data.remote.dto_mapper

import com.example.stackoverflowclone.data.remote.dto.QuestionDto
import com.example.stackoverflowclone.domain.models.Question

/**
 * Created by Mehul Bisht on 31-10-2021
 */

fun QuestionDto.toQuestion(): Question =
    Question(
        answer_count = this.answer_count,
        creation_date = this.creation_date,
        is_answered = this.is_answered,
        last_activity_date = this.last_activity_date,
        last_edit_date = this.last_edit_date,
        link = this.link,
        owner = this.owner.toOwner(),
        question_id = this.question_id,
        score = this.score,
        tags = this.tags,
        title = this.title,
        view_count = this.view_count
    )