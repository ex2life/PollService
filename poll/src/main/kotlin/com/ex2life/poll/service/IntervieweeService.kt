package com.ex2life.poll.service

import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question

interface IntervieweeService {
    fun addInterviewee(interviewee: Interviewee): Interviewee
    fun findByPoll(poll: Poll):List<Interviewee>
    fun findIntervieweeById(id_interviewee: Int): Interviewee
}