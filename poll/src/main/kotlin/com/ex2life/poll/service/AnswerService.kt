package com.ex2life.poll.service

import com.ex2life.poll.entity.Answer
import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question

interface AnswerService {
    fun addAnswer(answer: Answer): Answer
    fun findByInterviewee(interviewee: Interviewee):List<Answer>
    fun findByQuestion(question: Question):List<Answer>
}