package com.ex2life.pollauth.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User (
        @Id @GeneratedValue var id: Int? = null,
        var login: String = "",
        var name: String = "",
        var email: String = "",
        var password_hash: String = "",
        var date: Date = Date()
)