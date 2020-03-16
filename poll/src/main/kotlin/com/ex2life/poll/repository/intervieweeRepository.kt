package com.ex2life.poll.repository

import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface intervieweeRepository: JpaRepository <Interviewee, Int> {
    @Query("FROM Interviewee WHERE poll_id = :poll_id")
    fun findByPollId(poll_id: Int): List<Interviewee>
}