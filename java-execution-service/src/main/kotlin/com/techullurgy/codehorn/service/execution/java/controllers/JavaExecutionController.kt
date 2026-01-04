package com.techullurgy.codehorn.service.execution.java.controllers

import com.techullurgy.codehorn.common.code.execution.services.CodeExecutionService
import com.techullurgy.codehorn.common.code.execution.services.UserFolderCreator
import com.techullurgy.codehorn.common.models.ParsedTestcase
import com.techullurgy.codehorn.common.models.TestcaseResult
import com.techullurgy.codehorn.common.utils.toReadableString
import org.springframework.beans.factory.ObjectProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


data class CodeExecutionRequest(
    val executionId: String,
    val executableCode: String,
    val parsedTestcases: List<ParsedTestcase>
)

@RestController
class JavaExecutionController(
    private val codeExecutionServiceProvider: ObjectProvider<CodeExecutionService>,
    private val userFolderCreatorProvider: ObjectProvider<UserFolderCreator>,
) {
    @PostMapping
    fun executeCode(
        @RequestBody request: CodeExecutionRequest
    ): List<TestcaseResult> {
        val testcaseResults = userFolderCreatorProvider.getObject(request.executionId).use {
            codeExecutionServiceProvider.getObject(request.executionId).run {
                executeFor(
                    it.folder,
                    request.executableCode.toReadableString(),
                    request.parsedTestcases,
                )
            }
        }

        return testcaseResults
    }
}

/*

Gateway


Problems

static_codes {
    language:
    imports:
    utils:
}

dynamic_codes {
    language:
    problem_id:
    expected:
    main:
}


Practice


Evaluation
1. Differentiate Run / Submit
2. Identify Language
3. build file content (executable) - Base64

imports (static) + utils (static) + expected + actual + main (template)

executed_codes {
    evaluation_id:
    full_code:
}

4. parse testcases & generate content (executable) - b64
5. Send to execution service, wait for result

Java
1. Writing to files (file content and testcases)
2. Execute docker commands
3. Generate result
4. Deleting files/images
5. Send response

Daemon

 */

/*
CreateNecessaryTestcaseFilesUsecase
 */