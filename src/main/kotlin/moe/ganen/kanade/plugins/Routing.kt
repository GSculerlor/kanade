package moe.ganen.kanade.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import moe.ganen.kanade.database.KanadeDb
import moe.ganen.kanade.model.Score

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }

        get("/musics") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

            call.respond(KanadeDb.getMusics(page, limit))
        }

        get("/music/{id}") {
            val requestedId = call.parameters["id"]
            if (requestedId.isNullOrEmpty()) {
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

        get("/committer") {
            val lastCommitter = KanadeDb.getLastCommitter()
            if (lastCommitter == null) {
                call.respondText("no last committer saved")
            } else {
                call.respond(lastCommitter)
            }
        }

        route("/score") {
            get("/{songId}") {
                val songId = call.parameters["songId"]
                if (songId.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 3

                call.respond(KanadeDb.getScoresBySongId(songId.toInt(), page, limit))
            }

            post {
                try {
                    val score = call.receive<Score>()
                    KanadeDb.addScore(score)

                    call.respondText("Score stored correctly", status = HttpStatusCode.Created)
                } catch (ex: ContentTransformationException) {
                    call.respondText("failed to store Score", status = HttpStatusCode.UnsupportedMediaType)
                }
            }
        }
    }
}
