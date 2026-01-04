package com.techullurgy.codehorn.common.code.execution.services

import com.techullurgy.codehorn.common.code.execution.providers.VolumeMountPathProvider
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.io.Closeable
import java.io.File

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class UserFolderCreator(
    executionId: String,
): Closeable {

    @Autowired
    private lateinit var volumeMountPathProvider: ObjectProvider<VolumeMountPathProvider>

    val folder = File(
        volumeMountPathProvider.getObject(executionId).provideSourceVolumeMountPath(),
    ).apply { mkdirs() }

    init {
        File("${folder.path}/outputs").mkdir()
    }

    override fun close() {
        folder.deleteRecursively()
    }
}