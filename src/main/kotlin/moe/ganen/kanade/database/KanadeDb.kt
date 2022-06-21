package moe.ganen.kanade.database

import com.mongodb.client.model.Filters
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant
import moe.ganen.kanade.model.Score
import moe.ganen.sekai.response.Committer
import moe.ganen.sekai.response.Music
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.id.UUIDStringIdGenerator
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
            .descendingSort(Music::id)
            .skip(skip = (page - 1) * limit)
            .limit(limit = limit)
            .partial(true)
            .toList()
    }

    suspend fun getMusicById(id: Int): Music? {
        return database.getCollection<Music>("musics").findOne(Filters.eq("id", id))
    }

    suspend fun getLastCommitter(): Committer? {
        return database.getCollection<Committer>().findOne()
    }

    suspend fun compareLastUpdate(lastCommitter: Committer, onUpdate: suspend () -> Unit) {
        val collection = database.getCollection<Committer>()
        val lastClientUpdate = collection.findOne()
        // Client first time fetching the data
        if (lastClientUpdate == null) {
            collection.insertOne(lastCommitter)
            onUpdate()
            return
        }

        try {
            val localUpdateDate = lastClientUpdate.date.toInstant()
            val lastCommitDate = lastCommitter.date.toInstant()

            if (lastCommitDate > localUpdateDate) {
                collection.apply {
                    deleteOne(Filters.eq("date", lastClientUpdate.date))
                    insertOne(lastCommitter)

                    onUpdate()
                }
            }
        } catch (ex: IllegalArgumentException) {
            // do nothing if we failed to parse the date
        }
    }

    suspend fun addScore(score: Score) {
        database.getCollection<Score>("scores")
            .insertOne(
                score.copy(
                    id = UUIDStringIdGenerator.generateNewId<Score>().toString(),
                    submittedTime = Clock.System.now().epochSeconds
                )
            )
    }

    suspend fun getScoresBySongId(songId: Int, page: Int = 1, limit: Int = 3): List<Score> {
        return database.getCollection<Score>("scores")
            .find(Filters.eq("songId", songId))
            .descendingSort(Score::submittedTime)
            .skip(skip = (page - 1) * limit)
            .limit(limit = limit)
            .partial(true)
            .toList()
    }
}