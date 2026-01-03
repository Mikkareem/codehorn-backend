package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.providers.EntryPointProvider
import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import com.techullurgy.codehorn.common.code.execution.services.FileService
import com.techullurgy.codehorn.common.models.ProblemTestcase
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateEntryPointFileUseCase {

    @Autowired
    private lateinit var entryPointProvider: ObjectProvider<EntryPointProvider>

    @Autowired
    private lateinit var inputFilePathProvider: ObjectProvider<InputFilepathProvider>

    operator fun invoke(submissionId: String, testcases: List<ProblemTestcase>) {
        val inputFilePath = inputFilePathProvider.getObject(submissionId).provide()

        val entryPointContent = entryPointProvider.getObject(testcases).provide()

        FileService.writeFile(filePath = "$inputFilePath/entrypoint.sh", value = entryPointContent)
    }
}