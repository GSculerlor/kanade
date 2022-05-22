package moe.ganen.kanade.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CheckLastUpdateScheduler(
    private val interval: Long,
    private val initialDelay: Long,
    private val update: suspend () -> Unit
) :
    CoroutineScope { // implement CoroutineScope to create local scope
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    // this method will help to stop execution of a coroutine.
    // Call it to cancel coroutine and to break the while loop defined in the coroutine below
    fun cancel() {
        job.cancel()
    }

    fun schedule() = launch {
        // delay the first invocation
        delay(initialDelay)

        // launching the coroutine
        while (isActive) {
            update()
            delay(interval)
        }
    }
}