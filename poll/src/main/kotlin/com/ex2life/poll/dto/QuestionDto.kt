package com.ex2life.poll.dto

import java.sql.Date

data class QuestionDto (
    val id: Int,
    val name: String,
    val poll_id: Int
)