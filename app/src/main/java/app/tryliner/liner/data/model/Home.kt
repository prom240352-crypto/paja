package app.tryliner.liner.data.model

import com.google.gson.annotations.SerializedName

data class DailyMixItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("clusterArtists") val clusterArtists: List<String>?,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("trackCount") val trackCount: Int,
    @SerializedName("tracks") val tracks: List<Track>?
)

data class DailyMixesResponse(
    @SerializedName("items") val items: List<DailyMixItem>,
    @SerializedName("generatedAt") val generatedAt: String?
)

data class PopularTrackItem(
    @SerializedName("rank") val rank: Int,
    @SerializedName("track") val track: Track,
    @SerializedName("playCount") val playCount: Int,
    @SerializedName("uniqueListeners") val uniqueListeners: Int,
    @SerializedName("score") val score: Double
)

data class PopularTracksResponse(
    @SerializedName("window") val window: String,
    @SerializedName("generatedAt") val generatedAt: String?,
    @SerializedName("items") val items: List<PopularTrackItem>
)
