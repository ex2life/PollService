package com.ex2life.poll.client

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Primary
@FeignClient("PollAuthService", fallback = userServiceClientFallback::class)
interface userServiceClient {
    @GetMapping("/get_user")
    fun getUser(@RequestParam("id_user")id_user: Int, @RequestParam("token")token: String):String
    @GetMapping("/get_user_name")
    fun getUserName(@RequestParam("id_user")id_user: Int):String
    @PostMapping("/add_user")
    fun addUser(@RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("login")login: String, @RequestParam("password")password: String):Boolean
    @PostMapping("/update_user")
    fun updateUser(@RequestParam("id_user")id_user: String, @RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("password")password: String, @RequestParam("token")token: String):Boolean
    @PostMapping("/auth_user")
    fun authUser(@RequestParam("login")login: String, @RequestParam("password")password: String):String
    @PostMapping("/check_token")
    fun checkUser(@RequestParam("id")id: Int, @RequestParam("token")token: String):Boolean
}



@Component
class userServiceClientFallback: userServiceClient {
    override  fun getUser(id_user: Int, token: String)= "{\n" +
            "    \"id\": 0,\n" +
            "    \"login\": \"login\",\n" +
            "    \"name\": \"AnonimUser\",\n" +
            "    \"email\": \" AnonimUser@mail.anonim\",\n" +
            "    \"password_hash\": \"password\",\n" +
            "    \"date\": \"2020-03-12T12:52:16.677+0000\"\n" +
            "}"
    override fun getUserName(id_user: Int)="AnonimUser"
    override fun addUser(@RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("login")login: String, @RequestParam("password")password: String)=false
    override fun updateUser(@RequestParam("id_user")id_user: String, @RequestParam("email")email: String, @RequestParam("name")name: String, @RequestParam("password")password: String, @RequestParam("token")token: String)=false
    override fun authUser(@RequestParam("login")login: String, @RequestParam("password")password: String)="{\n" +
            "    \"id\": 0,\n" +
            "    \"token\": \"\"\n" +
            "}"
    override fun checkUser(@RequestParam("id")id: Int, @RequestParam("token")token: String)=false
}