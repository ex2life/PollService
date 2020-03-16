package com.ex2life.poll.service

import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question

interface QuestionService {
    fun addQuestion(question: Question): String
    fun findByPoll(poll: Poll):List<Question>
    fun findQuestionById(id:Int): Question
}