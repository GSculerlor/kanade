package moe.ganen.sekai.response

@kotlinx.serialization.Serializable
data class Music(
    val id: Int,
    val seq: Int,
    val releaseConditionId: Int,
    val categories: List<String>,
    val title: String,
    val lyricist: String,
    val composer: String,
    val arranger: String,
    val dancerCount: Int,
    val selfDancerPosition: Int,
    val assetbundleName: String,
    val liveTalkBackgroundAssetbundleName: String,
    val publishedAt: Long,
    val liveStageId: Int,
    val fillerSec: Double
)
