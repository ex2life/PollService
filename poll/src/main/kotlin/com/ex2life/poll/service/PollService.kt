package com.ex2life.poll.service

import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question

interface PollService {
    fun addPoll(poll: Poll): Poll
    fun findPollsByUserId(userId:Int): List<Poll>
    fun findPollById(id:Int): Poll
    fun delPoll(id: Int): Boolean
    fun kolvo(): Long
    fun getAll(): List<Poll>
}