package com.techullurgy.codehorn.service.gateway.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/practice")
class PracticeController {

    @GetMapping
    fun getPractice(): String {
        return "Hello from the PracticeController of the Gateway Service!!"
    }
}