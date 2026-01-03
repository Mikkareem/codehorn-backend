package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.models.CodeSubmissionResult
import com.techullurgy.codehorn.common.models.ProblemTestcase
import org.springframework.stereotype.Component

@Component
class ExecuteForResultsUseCase(
    private val executeDockerImage: ExecuteDockerImageUseCase
) {
    operator fun invoke(
        submissionId: String,
        testcases: List<ProblemTestcase>,
        isImageAvailable: Boolean
    ): Map<String, CodeSubmissionResult> {

        val imageName = "${Compiler.BASE_IMAGE_PREFIX}-$submissionId".lowercase()

        val results = buildMap {
            testcases.forEach {
                this[it.id] = CodeSubmissionResult.NotExecuted
            }
        }.toMutableMap()

        if(!isImageAvailable) {
            return results
        }

        val outputs = executeDockerImage(submissionId, imageName)

        if(outputs.contains("COMPILATION_ERROR")) {
            results.forEach {
                results[it.key] = CodeSubmissionResult.CompilationError
            }
            return results
        }

        val outputMap = outputs.associate { it.split("-")[0] to it.split("-")[1] }

        testcases.forEach{ testcase ->
            results[testcase.id] = when(outputMap[testcase.id]) {
                "TIME_LIMIT_EXCEEDED" -> CodeSubmissionResult.TimeLimitExceeded
                "RUNTIME_ERROR" -> CodeSubmissionResult.RuntimeError
                "ACCEPTED" -> CodeSubmissionResult.Accepted
                "WRONG_ANSWER" -> CodeSubmissionResult.WrongAnswer
                else -> CodeSubmissionResult.NotExecuted
            }
        }

        return results
    }
}