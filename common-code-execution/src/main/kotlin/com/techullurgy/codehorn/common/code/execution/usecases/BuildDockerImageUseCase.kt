package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.providers.InputFilepathProvider
import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.utils.errors
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class BuildDockerImageUseCase {

    @Autowired
    private lateinit var inputFilePathProvider: ObjectProvider<InputFilepathProvider>

    private val logger = LoggerFactory.getLogger(this::class.java)

    operator fun invoke(submissionId: String): Boolean {
        val imageName = "${Compiler.BASE_IMAGE_PREFIX}-$submissionId".lowercase()
        val inputFilePath = inputFilePathProvider.getObject(submissionId).provide()

        val builder = ProcessBuilder("docker", "build", "-t", imageName, inputFilePath)
        val process = builder.start()
        val isNotAborted = process.waitFor(10, TimeUnit.SECONDS)

        return if (isNotAborted) {
            if (process.exitValue() == 0) {
                logger.info("Docker image $imageName created successfully")
                true
            } else {
                logger.error(process.errors())
                false
            }
        } else {
            false
        }
    }
}