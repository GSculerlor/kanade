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
    implementation("io.ktor:ktor-client-core:2.0.2")
    implementation("io.ktor:ktor-client-apache:2.0.2")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.0.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.2")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}