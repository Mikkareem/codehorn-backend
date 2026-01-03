package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.providers.ExecutionParametersProvider
import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import com.techullurgy.codehorn.common.code.execution.services.FileService
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateDockerFileUseCase(
    private val executionParametersProvider: ExecutionParametersProvider,
) {
    @Autowired
    private lateinit var inputFilePathProvider: ObjectProvider<InputFilepathProvider>

    operator fun invoke(submissionId: String) {
        val compiler = executionParametersProvider.compiler
        val fileName = executionParametersProvider.codeFileName
        val language = executionParametersProvider.language

        val inputFilePath = inputFilePathProvider.getObject(submissionId).provide()

        val dockerFileContent = """
            |FROM $compiler
            |ADD ./$fileName /tmp/${language.lowercase()}/$fileName
            |COPY ./testcases /tmp/${language.lowercase()}/testcases
            |ADD ./entrypoint.sh /tmp/${language.lowercase()}/entrypoint.sh
            |WORKDIR /tmp/${language.lowercase()}/
            |RUN chmod +x entrypoint.sh
            |ENTRYPOINT ["./entrypoint.sh"]
        """.trimMargin()

        FileService.writeFile(filePath = "$inputFilePath/Dockerfile", value = dockerFileContent)
    }
}