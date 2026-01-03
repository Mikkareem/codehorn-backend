package com.techullurgy.codehorn.service.evaluation.clients

import org.springframework.context.annotation.Configuration
import org.springframework.web.service.registry.ImportHttpServices

@Configuration
@ImportHttpServices(
    basePackages = ["com.techullurgy.codehorn.service.evaluation.clients"],
    types = [ProblemsClient::class],
    group = "codehorn"
)
internal class HttpClientsConfig