package com.ex2life.poll.service.impl

import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import com.ex2life.poll.repository.pollRepository
import com.ex2life.poll.repository.questionRepository
import com.ex2life.poll.service.PollService
import com.ex2life.poll.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service




@Service
class QuestionServiceImpl (
        //private val pollRepository: pollRepository
) : QuestionService   {

    @Autowired
    lateinit var questionRepository: questionRepository

    override fun addQuestion(question: Question): String {
        questionRepository.saveAndFlush(question)
        return ("ok")
    }

    override fun findByPoll(poll: Poll): List<Question> {
        return questionRepository.findByPollId(poll.id!!)

    }

    override fun findQuestionById(id: Int): Question {
        return questionRepository.findById(id).orElse(Question())
    }
}