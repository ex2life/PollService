package com.ex2life.pollauth.service.impl


import com.ex2life.pollauth.entity.User
import com.ex2life.pollauth.repository.userRepository
import com.ex2life.pollauth.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service




@Service
class UserServiceImpl () : UserService   {

    @Autowired
    lateinit var userRepository: userRepository

    override fun addUser(user: User): Boolean {

        userRepository.saveAndFlush(user)
        return true
    }

    override fun updateUser(user: User): Boolean{
        userRepository.saveAndFlush(user)
        return true
    }

    override fun getUserName(id: Int): String {
        val user=userRepository.findById(id).orElse(User())
        return user.name
    }

    override fun findUserByLogin(login: String): User {
        return userRepository.findByLogin(login).first()
    }

    override fun findUserById(id_user: Int): User {
        return userRepository.findById(id_user).orElse(User())
    }

    override fun kolvo(): Long {
        return (userRepository.count())
    }
    override fun getAll(): List<User> {
        return (userRepository.findAll())
    }
}