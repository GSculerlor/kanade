package moe.ganen.kanade.database

import com.mongodb.client.model.Filters
import moe.ganen.sekai.Music
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object KanadeDb {
    private val client = KMongo.createClient("mongodb://localhost:27017").coroutine
    private val database = client.getDatabase("kanade")

    suspend fun addMusics(musics: List<Music>) {
        database.getCollection<Music>("musics").insertMany(musics)
    }

    suspend fun getMusics(page: Int = 1, limit: Int = 10): List<Music> {
        return database.getCollection<Music>("musics")
            .find()
            .skip(skip = (page - 1) * limit)
            .limit(limit = limit)
            .partial(true)
            .toList()
    }

    suspend fun getMusicById(id: Int): Music? {
        return database.getCollection<Music>("musics").findOne(Filters.eq("id", id))
    }
}