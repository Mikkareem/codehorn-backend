package com.techullurgy.codehorn.common.code.execution.providers

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DefaultTestcaseProvider(
    private val sampleTestcases: Set<String>,
    private val hiddenTestcases: Set<String>,
): TestcaseProvider {
    override fun sampleIds(): Set<String> = sampleTestcases
    override fun hiddenIds(): Set<String> = hiddenTestcases
}