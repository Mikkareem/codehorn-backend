import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.deps.mgmt) apply false
}

group = "com.techullurgy.codehorn"

allprojects {
    version = "1.0.0"
    repositories {
        mavenCentral()
    }
}

subprojects {
    // Configure Java toolchain
//    extensions.configure<JavaPluginExtension> {
//        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
//    }

    // Configure Kotlin compiler options
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict", "-Xannotation-default-target=param-property"))
            jvmTarget.set(JvmTarget.JVM_25)
        }
    }
}

// Disable bootJar in the root project (since it's not a Spring Boot app)
tasks.withType<BootJar>().configureEach {
    enabled = false
}