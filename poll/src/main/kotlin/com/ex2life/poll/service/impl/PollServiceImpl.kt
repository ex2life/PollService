package com.ex2life.poll.service.impl

import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import com.ex2life.poll.repository.pollRepository
import com.ex2life.poll.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


@Service
class PollServiceImpl (
        //private val pollRepository: pollRepository
) : PollService   {

    @Autowired
    lateinit var pollRepository: pollRepository

    override fun addPoll(poll: Poll): Poll {
        return pollRepository.saveAndFlush(poll)
    }

    override fun delPoll(id: Int): Boolean {
        pollRepository.deleteById(id)
        return true
    }

    override fun findPollsByUserId(userId: Int): List<Poll> {
        return pollRepository.findByUserId(userId)
    }

    override fun findPollById(id: Int): Poll {
        return pollRepository.findById(id).orElse(Poll())
    }

    override fun kolvo(): Long {
        return (pollRepository.count())
    }

    override fun getAll(): List<Poll> {
        return (pollRepository.findAll(Sort.by(Sort.Direction.DESC, "date")))
    }
}