package moe.ganen.sekai

import moe.ganen.sekai.response.Music
object Sekai {
    suspend fun fetchMusicsFromRemote(
        onSuccess: suspend (List<Music>) -> Unit,
        onFailure: suspend (Throwable) -> Unit
    ) {
        request<List<Music>>("https://gitcdn.link/cdn/Sekai-World/sekai-master-db-diff/master/musics.json")
            .onSuccess { onSuccess(it) }
            .onFailure { onFailure(it) }
    }

    fun fetchRemoteLastUpdate() {
        TODO("Not implemented")
    }
}