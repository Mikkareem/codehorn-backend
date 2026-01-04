package com.techullurgy.codehorn.service.execution.c.domain

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import org.springframework.stereotype.Component

@Component
class CExecutionParametersProvider: ExecutionParametersProvider {
    override val codeFileName: String = Compiler.C_INPUT_FILE_NAME
    override val compiler: String = Compiler.FROM_DOCKER_IMAGE_FOR_C_COMPILER
}