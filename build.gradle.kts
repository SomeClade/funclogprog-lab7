plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "1.8.0"
    application
    id("org.owasp.dependencycheck") version "8.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-server-netty:2.2.0")
    implementation("io.ktor:ktor-server-core:2.2.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.2.0")
    implementation("io.ktor:ktor-serialization-gson:2.2.0")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
}

application {
    mainClass.set("MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21" // Убедитесь, что у вас установлена эта версия JDK
    }
}

// Task for running dependency check
tasks.named("check").configure {
    dependsOn("dependencyCheckAnalyze")
}
