plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "moe.ganen"
version = "0.0.1"
application {
    mainClass.set("moe.ganen.kanade.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
    implementation("io.ktor:ktor-server-core-jvm:2.0.2")
    implementation("io.ktor:ktor-server-websockets-jvm:2.0.2")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.0.2")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.0.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.0.2")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.litote.kmongo:kmongo-coroutine:4.6.0")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.0")
    implementation(project(":sekai"))
}