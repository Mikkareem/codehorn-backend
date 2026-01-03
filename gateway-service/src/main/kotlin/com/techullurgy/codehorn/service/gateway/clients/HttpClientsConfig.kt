package com.techullurgy.codehorn.service.gateway.clients

import org.springframework.context.annotation.Configuration
import org.springframework.web.service.registry.ImportHttpServices

@Configuration
@ImportHttpServices(
    basePackages = ["com.techullurgy.codehorn.service.gateway.clients"],
    types = [PracticeClient::class, ProblemsClient::class, DailyChallengeClient::class, ContestsClient::class],
    group = "codehorn"
)
class HttpClientsConfig