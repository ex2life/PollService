package com.ex2life.poll.dto

import java.sql.Date

data class IntervieweeDto (
    val id: Int,
    val user_id: Int,
    val poll_id: Int,
    val date: Date
)