package moe.ganen.sekai

import moe.ganen.sekai.response.CommitResponse
import moe.ganen.sekai.response.Music

object Sekai {
    suspend fun fetchMusicsFromRemote(
        onSuccess: suspend (List<Music>) -> Unit,
        onFailure: suspend (Throwable) -> Unit
    ) {
        // TODO: 21/05/2022 use Github raw instead of gitcdn after I know how to "alter" response header
        request<List<Music>>("https://gitcdn.link/cdn/Sekai-World/sekai-master-db-diff/master/musics.json")
            .onSuccess { onSuccess(it) }
            .onFailure { onFailure(it) }
    }

    suspend fun fetchRemoteLastUpdate(
        onSuccess: suspend (CommitResponse) -> Unit,
        onFailure: suspend (Throwable) -> Unit
    ) {
        request<List<CommitResponse>>("https://api.github.com/repos/Sekai-World/sekai-master-db-diff/commits")
            .onSuccess { onSuccess(it.first()) }
            .onFailure { onFailure(it) }
    }
}