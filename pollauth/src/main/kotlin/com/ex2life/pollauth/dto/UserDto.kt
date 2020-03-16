package com.ex2life.pollauth.dto

import java.sql.Date

data class UserDto (
        val id: Int,
        val login: String,
        val name: String,
        val email: String,
        val password_hash: String,
        val date: Date
)