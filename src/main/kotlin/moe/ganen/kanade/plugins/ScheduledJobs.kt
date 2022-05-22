package moe.ganen.kanade.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.launch
import moe.ganen.kanade.database.KanadeDb
import moe.ganen.kanade.util.CheckLastUpdateScheduler
import moe.ganen.sekai.Sekai
import moe.ganen.sekai.response.Committer
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

fun Application.configureScheduledJobs() {
    val scheduler =
        CheckLastUpdateScheduler(interval = 30.minutes.toLong(DurationUnit.MILLISECONDS), initialDelay = 6000) {
            log.info("run checking last update on \"Sekai-World/sekai-master-db-diff\"")

            Sekai.fetchRemoteLastUpdate(onSuccess = {
                log.info("success checking last update with result: $it")
                compareLastUpdate(it.commit.committer) {
                    log.info("success saving new last update to client, updating musics...")
                    fetchMusics(
                        onSuccess = { log.info("success updating musics") },
                        onFailure = { throwable -> log.error("failed updating musics", throwable) }
                    )
                }
            }, onFailure = { log.error("failed checking last update", it) })
        }

    launch {
        scheduler.schedule()
    }
}

private suspend fun compareLastUpdate(committer: Committer, onUpdate: suspend () -> Unit) {
    KanadeDb.compareLastUpdate(committer, onUpdate)
}

private suspend fun fetchMusics(onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
    Sekai.fetchMusicsFromRemote(
        onSuccess = {
            KanadeDb.addMusics(it)
            onSuccess()
        },
        onFailure = onFailure
    )
}