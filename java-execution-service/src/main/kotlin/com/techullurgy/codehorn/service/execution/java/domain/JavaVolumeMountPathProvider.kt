package com.techullurgy.codehorn.service.execution.java.domain

import com.techullurgy.codehorn.common.code.execution.providers.VolumeMountPathProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class JavaVolumeMountPathProvider(
    private val executionId: String
): VolumeMountPathProvider {

    @Value($$"${codehorn.java.execution.base.path.source}")
    private lateinit var sourceBasePath: String

    @Value($$"${codehorn.java.execution.base.path.destination}")
    private lateinit var destinationBasePath: String

    override fun provideSourceVolumeMountPath(): String = "${sourceBasePath.removeSuffix("/")}/$executionId"

    override fun provideDestinationVolumeMountPath(): String = "${destinationBasePath.removeSuffix("/")}/$executionId"
}