package com.ex2life.poll.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
data class Question (
        @Id @GeneratedValue var id: Int? = null,
        var name: String = "",
        var poll_id:Int=0
)