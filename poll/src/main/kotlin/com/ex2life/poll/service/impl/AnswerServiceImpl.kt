package com.ex2life.poll.service.impl

import com.ex2life.poll.entity.Answer
import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import com.ex2life.poll.repository.answerRepository
import com.ex2life.poll.repository.intervieweeRepository
import com.ex2life.poll.repository.pollRepository
import com.ex2life.poll.service.AnswerService
import com.ex2life.poll.service.IntervieweeService
import com.ex2life.poll.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


@Service
class AnswerServiceImpl (
        //private val pollRepository: pollRepository
) : AnswerService{

    @Autowired
    lateinit var answerRepository: answerRepository

    override fun addAnswer(answer: Answer): Answer {
        return answerRepository.saveAndFlush(answer)
    }

    override fun findByInterviewee(interviewee: Interviewee): List<Answer> {
        return answerRepository.findByInterviewee_id(interviewee.id!!)

    }

    override fun findByQuestion(question: Question): List<Answer> {
        return answerRepository.findByQuestion_id(question.id!!)

    }
}