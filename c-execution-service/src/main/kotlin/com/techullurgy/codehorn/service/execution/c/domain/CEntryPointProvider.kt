package com.techullurgy.codehorn.service.execution.c.domain

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.providers.EntryPointProvider
import com.techullurgy.codehorn.common.code.execution.providers.TestcaseProvider
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class CEntryPointProvider(
    testcaseProvider: TestcaseProvider
): EntryPointProvider(testcaseProvider) {
    override fun runCommand(): String = "./runner"
    override fun compileCommand(): String = "gcc -w -o runner ${Compiler.C_INPUT_FILE_NAME}"
}