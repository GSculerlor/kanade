package moe.ganen.kanade.database

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object KanadeDb {
    val dbInstance = KMongo.createClient("mongodb://localhost:27017").coroutine
}