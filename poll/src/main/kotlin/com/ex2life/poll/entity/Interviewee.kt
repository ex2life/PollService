package com.ex2life.poll.entity

import java.util.*
import javax.persistence.*

@Entity
data class Interviewee (
        @Id @GeneratedValue var id: Int? = null,
        var user_id: Int = 0,
        var poll_id: Int = 0,
        var date: Date = Date()
)