package com.ex2life.poll.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
data class Answer (
        @Id @GeneratedValue var id: Int? = null,
        var interviewee_id:Int=0,
        var question_id:Int=0,
        var answer: String
)
