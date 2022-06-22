package moe.ganen.kanade

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import moe.ganen.kanade.plugins.*

fun main() {
    embeddedServer(Netty, port = 5002, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureScheduledJobs()
    }.start(wait = true)
}
