package moe.ganen.kanade.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.launch
import moe.ganen.kanade.util.CheckLastUpdateScheduler
import moe.ganen.sekai.Sekai

fun Application.configureScheduledJobs() {
    val scheduler = CheckLastUpdateScheduler(interval = 10800000, initialDelay = 500) {
        log.debug("run checking last update on \"Sekai-World/sekai-master-db-diff\"")

        Sekai.fetchRemoteLastUpdate(onSuccess = {
            log.debug("success checking last update with result: $it")
        }, onFailure = { log.error("failed checking last update", it) })
    }

    launch {
        scheduler.schedule()
    }
}