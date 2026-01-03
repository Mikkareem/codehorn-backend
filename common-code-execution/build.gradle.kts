import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps.mgmt)
}

dependencies {
    implementation(project(":common"))

    implementation(libs.boot.starter)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
}

tasks.withType<BootJar>().configureEach {
    enabled = false
}