package com.techullurgy.codehorn.service.execution.cpp.domain

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import org.springframework.stereotype.Component

@Component
class CppExecutionParametersProvider: ExecutionParametersProvider {
    override val codeFileName: String = Compiler.CPP_INPUT_FILE_NAME
    override val language: String = "cpp"
    override val compiler: String = Compiler.FROM_DOCKER_IMAGE_FOR_CPP_COMPILER
}