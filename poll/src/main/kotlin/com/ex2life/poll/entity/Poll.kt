package com.ex2life.poll.entity

import java.util.*
import javax.persistence.*

@Entity
data class Poll (
        @Id @GeneratedValue var id: Int? = null,
        var name: String = "",
        var user_id: Int = 0,
        var date: Date = Date()
)