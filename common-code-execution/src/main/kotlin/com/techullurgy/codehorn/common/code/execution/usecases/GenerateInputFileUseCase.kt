package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import com.techullurgy.codehorn.common.code.execution.services.FileService
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GenerateInputFileUseCase(
    private val parametersProvider: ExecutionParametersProvider
) {
    @Autowired
    private lateinit var inputFilePathProvider: ObjectProvider<InputFilepathProvider>

    operator fun invoke(
        submissionId: String,
        fileContent: String
    ) {
        val inputFilePath = inputFilePathProvider.getObject(submissionId).provide()
        FileService.writeFile("$inputFilePath/${parametersProvider.codeFileName}", fileContent)
    }
}