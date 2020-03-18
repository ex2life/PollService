package com.ex2life.poll.controller

import com.ex2life.poll.client.userServiceClient
import com.ex2life.poll.entity.Answer
import com.ex2life.poll.entity.Interviewee
import com.ex2life.poll.entity.Poll
import com.ex2life.poll.entity.Question
import com.ex2life.poll.repository.pollRepository
import com.ex2life.poll.service.AnswerService
import com.ex2life.poll.service.IntervieweeService
import com.ex2life.poll.service.PollService
import com.ex2life.poll.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

data class IntervieweePlus (
        var interviewee: Interviewee = Interviewee(),
        var user_name: String = ""
)

data class Result (
        var title: String = "",
        var answer: String = ""
)

@Controller
class PollController(private val userService: userServiceClient) {

    @Autowired
    lateinit var pollService: PollService
    @Autowired
    lateinit var questionService: QuestionService
    @Autowired
    lateinit var intervieweeService: IntervieweeService
    @Autowired
    lateinit var answerService: AnswerService

    @GetMapping("/new-poll")
    fun new_poll(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        model["title"] = "Новый опрос"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")

        model["index"] = false
        return "new_poll"
    }

    @GetMapping("/del-poll/{id}")
    fun del_poll(@CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (!userService.checkUser(pollService.findPollById(id).user_id,token)) return "redirect:/error"
        if (user_id!="0"){
            pollService.delPoll(id)
        }
        return "redirect:/";
    }

    @PostMapping("/savepoll")
    fun newpoll(namepoll: String, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String ) :String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        var poll=pollService.addPoll(
                Poll(
                        name=namepoll,
                        user_id = user_id.toInt()
                )
        )
        return "redirect:/edit-poll/"+poll.id;
    }

    @GetMapping("/edit-poll/{id}")
    fun new_question(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int  ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (!userService.checkUser(pollService.findPollById(id).user_id,token)) return "redirect:/error"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")
        model["index"] = false
        var poll=pollService.findPollById(id)
        if (user_id.toInt()!=poll.user_id) return "redirect:/error"
        var questions=questionService.findByPoll(poll)
        model["title"] = poll.name
        model["poll"]=poll
        model["questions"]=questions
        model["size"]=questions.size
        return "edit_poll"
    }

    @PostMapping("/edit-poll/{id}")
    fun save_question(model: Model, questionText : String, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (!userService.checkUser(pollService.findPollById(id).user_id,token)) return "redirect:/error"
        if (user_id=="0") return ("redirect:/login")
        var poll=pollService.findPollById(id)
        questionService.addQuestion(
                Question(
                        name=questionText,
                        poll_id = poll.id!!
                )
        )
        return "redirect:/edit-poll/"+poll.id;
    }

    @GetMapping("/poll/{id}")
    fun process(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int  ) : String{
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        model["index"] = false
        var poll=pollService.findPollById(id)
        var questions=questionService.findByPoll(poll)
        model["title"] = poll.name
        model["poll"]=poll
        model["questions"]=questions
        model["size"]=questions.size
        return "poll"
    }

    @PostMapping("/save-result")
    fun saveResult(model: Model, poll_id:Int,  answer:Array<String>, question_id:Array<Int>,  @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String) : String {
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        var interviewee=intervieweeService.addInterviewee(Interviewee(
                user_id = user_id.toInt(),
                poll_id = poll_id
        ))
        for (i in answer.indices) {
            answerService.addAnswer(
                    Answer(
                            question_id= question_id[i],
                            interviewee_id = interviewee.id!!,
                            answer = answer[i]

                    )
            )
        }
        model["status"]="Спасибо за прохождение опроса"
        model["emphty"]=true
        model["title"]="Спасибо за прохождение опроса"
        return "thanks"
    }

    @GetMapping("/results/{id}")
    fun results(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int  ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (!userService.checkUser(pollService.findPollById(id).user_id,token)) return "redirect:/error"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")
        model["index"] = false
        var poll=pollService.findPollById(id)
        var questions=questionService.findByPoll(poll)
        var interviewees=intervieweeService.findByPoll(poll)
        val intervieweesPlus = mutableListOf<IntervieweePlus>()
        for (interviewee in interviewees) {
            intervieweesPlus.add(
                    IntervieweePlus(
                            interviewee=interviewee,
                            user_name = userService.getUserName(interviewee.user_id.toInt())
                    )
            )
        }
        model["title"] = poll.name
        model["poll"]=poll
        model["interviewees"]=intervieweesPlus
        model["size"]=questions.size
        return "results"
    }

    @GetMapping("/results/interviewee/{id}")
    fun results_interviewee(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int  ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")
        model["index"] = false
        var interviewee=intervieweeService.findIntervieweeById(id)
        var poll=pollService.findPollById(interviewee.poll_id)
        if (!userService.checkUser(poll.user_id,token)) return "redirect:/error"
        val answers=answerService.findByInterviewee(interviewee)
        var questions=questionService.findByPoll(poll)
        val answersList = mutableListOf<Result>()
        for (answer in answers) {
            answersList.add(
                    Result(
                            title = questionService.findQuestionById(answer.question_id).name,
                            answer = answer.answer
                    )
            )
        }
        model["title"] = poll.name
        model["poll"]=poll
        model["interviewee_name"]=userService.getUserName(interviewee.user_id)
        model["result"]=answersList
        model["size"]=questions.size
        return "results_interviewee"
    }

    @GetMapping("/results/question/{id}")
    fun results_question(model: Model, @CookieValue(value = "user_id", defaultValue = "0") user_id: String, @CookieValue(value = "token", defaultValue = "") token: String, @PathVariable id:Int  ) : String{
        if (!userService.checkUser(user_id.toInt(),token)) return "redirect:/error"
        if (user_id!="0"){
            model["auth"] = true
            model["user_name"]=userService.getUserName(user_id.toInt())
        }
        else return ("redirect:/login")
        model["index"] = false
        val question=questionService.findQuestionById(id)
        var answers=answerService.findByQuestion(question)
        var poll=pollService.findPollById(question.poll_id)
        if (!userService.checkUser(poll.user_id,token)) return "redirect:/error"
        val answersList = mutableListOf<Result>()
        for (answer in answers) {
            answersList.add(
                    Result(
                            title = userService.getUserName(intervieweeService.findIntervieweeById(answer.interviewee_id).user_id),
                            answer = answer.answer
                    )
            )
        }
        model["title"] = question.name
        model["poll"]=poll
        model["question_name"]=question.name
        model["result"]=answersList
        model["size"]=answersList.size
        return "results_question"
    }
}