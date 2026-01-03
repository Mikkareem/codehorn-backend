package com.techullurgy.codehorn.common.code.execution.services

import com.techullurgy.codehorn.common.code.execution.usecases.BuildDockerImageUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.CreateDockerFileUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.CreateEntryPointFileUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.CreateNecessaryTestcaseFilesUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.DeleteDockerImageUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.ExecuteForResultsUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.GenerateInputFileUseCase
import com.techullurgy.codehorn.common.code.execution.usecases.GenerateTestcaseResultsUseCase
import com.techullurgy.codehorn.common.models.ProblemTestcase
import com.techullurgy.codehorn.common.models.TestcaseResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.io.File

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class CodeExecutionService(
    private val submissionId: String
) {
    @Autowired private lateinit var createEntryPointFileUseCase: CreateEntryPointFileUseCase
    @Autowired private lateinit var createDockerFileUseCase: CreateDockerFileUseCase
    @Autowired private lateinit var buildDockerImageUseCase: BuildDockerImageUseCase
    @Autowired private lateinit var createNecessaryTestcaseFilesUseCase: CreateNecessaryTestcaseFilesUseCase
    @Autowired private lateinit var executeForResults: ExecuteForResultsUseCase
    @Autowired private lateinit var generateInputFileUseCase: GenerateInputFileUseCase
    @Autowired private lateinit var deleteDockerImageUseCase: DeleteDockerImageUseCase
    @Autowired private lateinit var generateTestcaseResultsUseCase: GenerateTestcaseResultsUseCase

    fun executeFor(
        folder: File,
        fileContent: String,
        testcases: List<ProblemTestcase>,
    ): List<TestcaseResult> {
        generateInputFileUseCase(submissionId, fileContent)

        createNecessaryTestcaseFilesUseCase(submissionId, testcases)

        createEntryPointFileUseCase(submissionId, testcases)

        createDockerFileUseCase(submissionId)

        val isCreated = buildDockerImageUseCase(submissionId)

        val results = executeForResults(submissionId, testcases, isCreated)

        if(isCreated) {
            deleteDockerImageUseCase(submissionId)
        }

        return generateTestcaseResultsUseCase(folder, testcases, results)
    }
}