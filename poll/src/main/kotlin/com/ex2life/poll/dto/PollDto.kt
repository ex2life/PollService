package com.ex2life.poll.dto

import java.sql.Date

data class PollDto (
        val id: Int,
        val name: String,
        val user_id: Int,
        val date: Date
)