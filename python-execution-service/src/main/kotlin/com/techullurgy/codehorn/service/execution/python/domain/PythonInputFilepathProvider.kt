package com.techullurgy.codehorn.service.execution.python.domain

import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PythonInputFilepathProvider(
    private val executionId: String
): InputFilepathProvider {
    override fun provide(): String = "temp/python/$executionId"
}