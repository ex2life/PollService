package com.ex2life.poll.repository

import com.ex2life.poll.entity.Answer
import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface answerRepository: JpaRepository <Answer, Int> {
    @Query("FROM Answer WHERE interviewee_id = :interviewee_id")
    fun findByInterviewee_id(interviewee_id: Int): List<Answer>
    @Query("FROM Answer WHERE question_id = :question_id")
    fun findByQuestion_id(question_id: Int): List<Answer>
}