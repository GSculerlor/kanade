val ktor_version: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "moe.ganen"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}