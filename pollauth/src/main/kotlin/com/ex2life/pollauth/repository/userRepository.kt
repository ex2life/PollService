package com.ex2life.pollauth.repository

import com.ex2life.pollauth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface userRepository: JpaRepository <User, Int> {
    @Query("FROM User WHERE login = :login")
    fun findByLogin(login: String): List<User>
}