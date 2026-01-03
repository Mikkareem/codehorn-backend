package com.techullurgy.codehorn.service.evaluation.clients

import org.springframework.web.service.annotation.HttpExchange

@HttpExchange("http://problems-service")
interface ProblemsClient {

}