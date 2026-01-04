package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.utils.Compiler
import com.techullurgy.codehorn.common.code.execution.utils.errors
import com.techullurgy.codehorn.common.code.execution.utils.outputs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class BuildDockerImageUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    operator fun invoke(
        userFolderPath: String,
        executionId: String
    ): Boolean {
        val imageName = "${Compiler.BASE_IMAGE_PREFIX}-$executionId".lowercase()

        val builder = ProcessBuilder("docker", "build", "-t", imageName, userFolderPath)
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
            logger.info("Docker image $imageName taking long time")
            logger.info("Build logs: {}", process.outputs())
            logger.info("Build errors: {}", process.errors())
            false
        }
    }
}