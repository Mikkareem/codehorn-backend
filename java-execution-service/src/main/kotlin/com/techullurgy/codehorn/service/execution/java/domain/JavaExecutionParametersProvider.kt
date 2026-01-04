package com.techullurgy.codehorn.service.execution.java.domain

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import org.springframework.stereotype.Component

@Component
class JavaExecutionParametersProvider: ExecutionParametersProvider {
    override val codeFileName: String = Compiler.JAVA_INPUT_FILE_NAME
    override val compiler: String = Compiler.FROM_DOCKER_IMAGE_FOR_JAVA_COMPILER
}