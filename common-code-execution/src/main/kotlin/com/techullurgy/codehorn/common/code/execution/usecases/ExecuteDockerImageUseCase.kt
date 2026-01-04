package com.techullurgy.codehorn.common.code.execution.usecases

import com.techullurgy.codehorn.common.code.execution.providers.VolumeMountPathProvider
import com.techullurgy.codehorn.common.code.execution.utils.errors
import com.techullurgy.codehorn.common.code.execution.utils.outputs
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.util.concurrent.TimeUnit

@Component
class ExecuteDockerImageUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var volumeMountPathProvider: ObjectProvider<VolumeMountPathProvider>

    operator fun invoke(executionId: String, imageName: String): List<String> {

        val volumeMountSourceBasePath = volumeMountPathProvider.getObject(executionId).provideSourceVolumeMountPath().run {
            File(this).absolutePath
        }
        val volumeMountDestinationBasePath = volumeMountPathProvider.getObject(executionId).provideDestinationVolumeMountPath().run {
            File(this).absolutePath
        }

        val builder = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            "$volumeMountSourceBasePath/outputs:$volumeMountDestinationBasePath/outputs",
            "--name",
            "$imageName-container",
            imageName
        )
        val process = builder.start()
        val isNotAborted = process.waitFor(2, TimeUnit.MINUTES)

        if (isNotAborted) {
            val outputs = process.outputs()
            val error = process.errors()
            if(error.isNotBlank()) {
                logger.error(error)
            }
            if(outputs.isNotEmpty()) {
                logger.debug(outputs.toString())
            }
            return outputs
        } else {
            logger.error("Aborting the container")
            forceStopAndDeleteContainer(imageName)
            process.destroy()
            process.waitFor()
        }

        return emptyList()
    }

    private fun forceStopAndDeleteContainer(imageName: String) {
        val builder = ProcessBuilder("docker", "stop", "$imageName-container")
        val process = builder.start()
        process.waitFor()
    }
}