package com.ex2life.pollauth.controller

import com.ex2life.pollauth.entity.User
import com.ex2life.pollauth.service.UserService
import com.google.gson.JsonObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.xml.bind.DatatypeConverter

data class UserAuth(
        var id: Int = 0,
        var token: String = ""
)

fun check_token(token:String, user: User) : Boolean{
    return token== gener_token(user)
}

fun gener_token(user: User) : String{
    return sha1(md5(user.login+"ex2life")+user.id+md5("kek"+user.password_hash))
}

fun sha1(input: String) = hashString("SHA-1", input)
fun md5(input: String) = hashString("MD5", input)

private fun hashString(type: String, input: String): String {
    val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
    return DatatypeConverter.printHexBinary(bytes).toUpperCase()
}

@RestController
class apiController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/get_user")
    fun getUser(@RequestParam("id_user") id_user: Int):User{
        return (userService.findUserById(id_user));
    }

    @GetMapping("/get_user_name")
    fun getUserName(@RequestParam("id_user") id_user: Int):String{
        return (userService.getUserName(id_user));
    }

    @PostMapping("/add_user")
    fun new_User(@RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("login")login: String, @RequestParam("password")password: String):Boolean{
        return userService.addUser(User(
                name=name,
                email=email,
                login=login,
                password_hash = BCryptPasswordEncoder().encode(password)
        ));
    }

    @PostMapping("/update_user")
    fun update_User(@RequestParam("id_user")id_user: Int, @RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("password")password: String):Boolean{
        val user=userService.findUserById(id_user)
        user.name=name
        user.email=email
        if (password!=="") user.password_hash = BCryptPasswordEncoder().encode(password)
        return userService.updateUser(user);
    }

    @PostMapping("/auth_user")
    fun old_User(@RequestParam("login")login: String, @RequestParam("password")password: String):UserAuth{
        val user=userService.findUserByLogin(login)
        if (BCryptPasswordEncoder().matches(password, user.password_hash)) return UserAuth(id=user.id!!,token= gener_token(user))
        else return UserAuth(id=0)
    }

    @PostMapping("/check_token")
    fun check_User(@RequestParam("id")id: Int, @RequestParam("token")token: String):Boolean{
        return check_token(token,userService.findUserById(id))
    }
}