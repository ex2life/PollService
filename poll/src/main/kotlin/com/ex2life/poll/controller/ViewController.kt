package com.ex2life.poll.controller

import org.springframework.stereotype.Controller
import com.ex2life.poll.client.userServiceClient
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.service.IntervieweeService
import com.ex2life.poll.service.PollService
import com.ex2life.poll.service.QuestionService
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.ui.set
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie
import com.google.gson.JsonParser

data class PollPlus (
        var poll: Poll = Poll(),
        var user_name: String = "",
        var root:Boolean = false,
        var size:Int = 0,
        var count:Int = 0
)

@Controller
class ViewController(private val userService: userServiceClient) {

    @Autowired
    lateinit var pollService: PollService
    @Autowired
    lateinit var questionService: QuestionService
    @Autowired
    lateinit var intervieweeService: IntervieweeService

    @GetMapping("/")
    fun index(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String ) : String{
        var polls=pollService.getAll()
        val pollsPlus = mutableListOf<PollPlus>()
        for (poll in polls) {
            val size=questionService.findByPoll(poll).size
            if (size>0){
                pollsPlus.add(
                        PollPlus(
                            poll=poll,
                            user_name = userService.getUserName(poll.user_id.toInt()),
                            root=(poll.user_id==user_id.toInt()),
                            size = size
                        )
                )
            }
        }
        model["polls"]=pollsPlus
        model["title"] = "Система опросов 'Poll'"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else model["auth"] = false
        model["index"] = true
        return "index"
    }

    @GetMapping("/mypolls")
    fun mypolls(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        var polls=pollService.findPollsByUserId(user_id.toInt())
        val pollsPlus = mutableListOf<PollPlus>()
        for (poll in polls) {
            val size=questionService.findByPoll(poll).size
            pollsPlus.add(
                    PollPlus(
                            poll=poll,
                            user_name = userService.getUserName(poll.user_id.toInt()),
                            root=(poll.user_id==user_id.toInt()),
                            size = size,
                            count= intervieweeService.findByPoll(poll).size
                    )
            )
        }
        model["polls"]=pollsPlus
        model["title"] = "Система опросов 'Poll'"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else model["auth"] = false
        model["index"] = false
        return "mypolls"
    }



    /*@GetMapping("/error")
    fun error(model: Model) : String{
        return "error"
    }*/


}