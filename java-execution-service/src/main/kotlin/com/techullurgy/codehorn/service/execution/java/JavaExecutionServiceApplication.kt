package com.techullurgy.codehorn.service.execution.java

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.techullurgy.codehorn"])
class JavaExecutionServiceApplication

fun main() {
    runApplication<JavaExecutionServiceApplication>()
}