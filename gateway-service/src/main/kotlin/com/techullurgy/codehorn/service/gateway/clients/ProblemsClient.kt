package com.techullurgy.codehorn.service.gateway.clients

import org.springframework.web.service.annotation.HttpExchange

@HttpExchange($$"${service.problems.baseUrl}")
interface ProblemsClient