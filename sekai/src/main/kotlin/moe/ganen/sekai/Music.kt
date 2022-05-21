package moe.ganen.sekai

import kotlinx.serialization.SerialName

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
    @SerialName("assetbundleName")
    val assetBundleName: String,
    @SerialName("liveTalkBackgroundAssetbundleName")
    val liveTalkBackgroundAssetBundleName: String,
    val publishedAt: Long,
    val liveStageId: Int,
    val fillerSec: Double
)
