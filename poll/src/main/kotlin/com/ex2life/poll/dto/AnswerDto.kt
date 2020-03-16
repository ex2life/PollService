package com.ex2life.poll.dto

import java.sql.Date

data class AnswerDto (
    val id: Int,
    val interviewee_id: Int,
    val question_id: Int,
    val answer: String
)