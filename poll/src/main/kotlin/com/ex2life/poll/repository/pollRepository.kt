package com.ex2life.poll.repository

import com.ex2life.poll.entity.Poll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface pollRepository: JpaRepository <Poll, Int> {
    @Query("FROM Poll WHERE user_id = :userId")
    fun findByUserId(userId: Int): List<Poll>
}