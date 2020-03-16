package com.ex2life.pollauth.service

import com.ex2life.pollauth.entity.User


interface UserService {
    fun addUser(user: User): Boolean
    fun updateUser(user: User): Boolean
    fun getUserName(id_user:Int): String
    fun findUserByLogin(login:String): User
    fun findUserById(id_user: Int): User
    fun kolvo(): Long
    fun getAll(): List<User>
}