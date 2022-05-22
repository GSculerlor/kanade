package moe.ganen.sekai.response

import com.google.gson.annotations.SerializedName

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
    @SerializedName("assetbundleName")
    val assetBundleName: String,
    @SerializedName("liveTalkBackgroundAssetbundleName")
    val liveTalkBackgroundAssetBundleName: String,
    val publishedAt: Long,
    val liveStageId: Int,
    val fillerSec: Double
)
