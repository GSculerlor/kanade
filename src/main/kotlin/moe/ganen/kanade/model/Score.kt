package moe.ganen.kanade.model

@kotlinx.serialization.Serializable
data class Score(
    val id: String? = null,
    val songId: Int,
    val score: Int,
    val perfect: Int,
    val great: Int = 0,
    val good: Int = 0,
    val bad: Int = 0,
    val miss: Int = 0,
    val submittedTime: Long = 0
)