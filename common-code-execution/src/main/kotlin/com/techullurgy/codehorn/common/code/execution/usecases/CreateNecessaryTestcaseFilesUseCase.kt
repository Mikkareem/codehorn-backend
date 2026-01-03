package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.parsers.TestcaseParserStrategy
import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import com.techullurgy.codehorn.common.code.execution.services.FileService
import com.techullurgy.codehorn.common.models.ProblemTestcase
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateNecessaryTestcaseFilesUseCase(
    private val testcaseParser: TestcaseParserStrategy
) {
    @Autowired
    private lateinit var inputFilePathProvider: ObjectProvider<InputFilepathProvider>

    operator fun invoke(submissionId: String, testcases: List<ProblemTestcase>) {
        val inputFilePath = inputFilePathProvider.getObject(submissionId).provide()

        testcases.filter { !it.isHidden }.forEach {
            val testcaseFilePath = "$inputFilePath/testcases/sample-input${it.id}.txt"
            FileService.writeFile(testcaseFilePath, testcaseParser.parse(it))
        }

        testcases.filter { it.isHidden }.forEach {
            val testcaseFilePath = "$inputFilePath/testcases/hidden-input${it.id}.txt"
            FileService.writeFile(testcaseFilePath, testcaseParser.parse(it))
        }
    }
}