import org.gradle.kotlin.dsl.withType
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps.mgmt)
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-code-execution"))

    implementation(libs.boot.starter.webmvc)
    implementation(libs.boot.starter.restclient)

    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)

    testImplementation(libs.boot.starter.webmvc.test)
    testImplementation(libs.boot.starter.restclient.test)

    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<BootJar>().configureEach {
    enabled = true
}