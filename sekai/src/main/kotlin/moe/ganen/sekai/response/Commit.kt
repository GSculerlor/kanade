package moe.ganen.sekai.response

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class CommitResponse(
    val sha: String,
    @SerializedName("node_id")
    val nodeId: String,
    val commit: Commit
)

@kotlinx.serialization.Serializable
data class Commit(
    val author: Committer,
    val committer: Committer
)

@kotlinx.serialization.Serializable
data class Committer(
    val name: String,
    val email: String,
    val date: String
)