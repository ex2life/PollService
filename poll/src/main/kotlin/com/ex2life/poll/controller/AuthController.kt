package com.ex2life.poll.controller

import com.ex2life.poll.client.userServiceClient
import com.ex2life.poll.service.PollService
import com.google.gson.JsonParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@Controller
class AuthController(private val userService: userServiceClient) {

    @Autowired
    lateinit var pollService: PollService

    @GetMapping("/register")
    fun new_user(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String ) : String{
        model["title"] = "Регистрация"
        model["auth"] = false
        model["index"] = false
        return "register"
    }

    @GetMapping("/login")
    fun old_user(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String ) : String{
        model["title"] = "Авторизация"
        model["auth"] = false
        model["index"] = false
        return "login"
    }

    @PostMapping("/new_user")
    fun save_new_user(email: String, name: String, login: String, password: String) :String{
        if (userService.addUser(email, name, login, password)) return "redirect:/"
        else return "redirect:/error"
    }

    @PostMapping("/auth")
    fun auth_user(model: Model, login: String, password: String, response: HttpServletResponse) :String {
        val json=userService.authUser(login, password)
        val json_obj= JsonParser().parse(json).getAsJsonObject()
        val id=json_obj.get("id").asInt
        val token=json_obj.get("token").asString
        if (id==0)
            model["status"] = "Такого пользователя нет, или пароль неверный"
        else {
            val cookie = Cookie("user_id",id.toString())
            val cookie2 = Cookie("token",token)
            response.addCookie(cookie)
            response.addCookie(cookie2)
            model["status"] = "Вы авторизованы успешно"

        }
        model["title"] = "Статус авторизации"
        return "login_status"
    }

    @GetMapping("/quit")
    fun quit_user(model: Model, response: HttpServletResponse) :String {
        val cookie = Cookie("user_id",null)
        val cookie2 = Cookie("token",null)

        response.addCookie(cookie)
        response.addCookie(cookie2)
        model["status"] = "Вы вышли успешно"
        model["title"] = "Статус авторизации"
        return "login_status"
    }

    @GetMapping("/profile")
    fun profile_user(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String ) : String{
        model["polls"]=pollService.getAll()
        model["title"] = "Ваш профиль"
        if (user_id!="0"){
            model["auth"] = true
            var json=userService.getUser(user_id.toInt())
            var json_obj= JsonParser().parse(json).getAsJsonObject()
            model["user_name"]=userService.getUserName(user_id.toInt())
            model["user_email"]=json_obj.get("email").asString
            model["user_login"]=json_obj.get("login").asString
        }
        else {
            model["auth"] = false
            return "redirect:/"
        }
        model["index"] = false
        return "profile"
    }

    @PostMapping("/update_user")
    fun update_user(email: String, name: String, password: String, @CookieValue(value = "user_id", defaultValue = "0") user_id: String) :String{
        if (userService.updateUser(user_id, email, name, password)) return "redirect:/quit"
        else return "redirect:/error"
    }
}