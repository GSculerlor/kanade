package moe.ganen.kanade.model

@kotlinx.serialization.Serializable
data class Score(
    val id: Int,
    val songId: Int,
    val score: Int,
    val perfect: Int,
    val great: Int = 0,
    val good: Int = 0,
    val bad: Int = 0,
    val miss: Int = 0
)