package moe.ganen.sekai

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

internal val sekaiClient = HttpClient(Apache) {
    install(ContentNegotiation) {
        gson()
    }

    engine {
        followRedirects = false
        connectTimeout = 10_000
        connectionRequestTimeout = 20_000
    }
}

internal suspend inline fun <reified T> request(
    url: String,
    blockBuilder: HttpRequestBuilder.() -> Unit = {},
): Result<T> = try {
    val result: T = sekaiClient.get(urlString = url, block = blockBuilder).body()
    Result.success(result)
} catch (ex: Exception) {
    Result.failure(ex)
}