package com.ex2life.poll.service.impl

import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import com.ex2life.poll.repository.intervieweeRepository
import com.ex2life.poll.repository.pollRepository
import com.ex2life.poll.service.IntervieweeService
import com.ex2life.poll.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


@Service
class IntervieweeServiceImpl (
        //private val pollRepository: pollRepository
) : IntervieweeService {

    @Autowired
    lateinit var intervieweeRepository: intervieweeRepository

    override fun addInterviewee(interviewee: Interviewee): Interviewee {
        return intervieweeRepository.saveAndFlush(interviewee)
    }

    override fun findByPoll(poll: Poll): List<Interviewee> {
        return intervieweeRepository.findByPollId(poll.id!!)

    }

    override fun findIntervieweeById(id_interviewee: Int): Interviewee {
        return intervieweeRepository.findById(id_interviewee).orElse(Interviewee())
    }

}