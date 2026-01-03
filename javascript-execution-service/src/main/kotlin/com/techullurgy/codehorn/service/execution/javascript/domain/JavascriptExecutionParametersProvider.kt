package com.techullurgy.codehorn.service.execution.javascript.domain

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import org.springframework.stereotype.Component

@Component
class JavascriptExecutionParametersProvider: ExecutionParametersProvider {
    override val codeFileName: String = Compiler.JAVASCRIPT_INPUT_FILE_NAME
    override val language: String = "javascript"
    override val compiler: String = Compiler.FROM_DOCKER_IMAGE_FOR_JAVASCRIPT_COMPILER
}