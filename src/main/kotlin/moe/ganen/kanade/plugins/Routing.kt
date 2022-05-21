package moe.ganen.kanade.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import moe.ganen.kanade.database.KanadeDb
import moe.ganen.sekai.Sekai

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }

        get("/musics") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

            Sekai.fetchMusicsFromRemote(onSuccess = {
                KanadeDb.addMusics(it)
            }, onFailure = { call.respondText("error: ${it.localizedMessage}") })

            call.respond(KanadeDb.getMusics(page, limit))
        }

        get("music/{id}") {
            val requestedId = call.parameters["id"]
            if (requestedId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                val music = KanadeDb.getMusicById(requestedId.toInt())
                if (music != null) {
                    call.respond(music)
                } else {
                    call.respond(Throwable("Music not found"))
                }
            } catch (ex: Exception) {
                call.respond(ex)
            }
        }
    }
}
