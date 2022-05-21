package moe.ganen.kanade.plugins

import com.mongodb.client.model.Filters
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import moe.ganen.kanade.database.KanadeDb
import moe.ganen.sekai.Music
import moe.ganen.sekai.Sekai

fun Application.configureRouting() {
    val db = KanadeDb.dbInstance.getDatabase("musics")

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }

        get("/musics") {
            Sekai.fetchMusicsFromRemote(
                onSuccess = {
                    db.getCollection<Music>("musics").insertMany(it)
                    call.respond(it)
                },
                onFailure = { call.respondText("error: ${it.localizedMessage}") }
            )
        }

        get("music/{id}") {
            val requestedId = call.parameters["id"]
            if (requestedId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val music = db.getCollection<Music>("musics").findOne(Filters.eq("id", requestedId))
            if (music != null) {
                call.respond(music)
            } else {
                call.respond(Throwable("Music not found"))
            }
        }
    }
}
