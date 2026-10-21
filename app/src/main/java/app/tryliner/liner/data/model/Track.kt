package app.tryliner.liner.data.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("artists") val artists: List<TrackArtist>?,
    @SerializedName("album") val album: String?,
    @SerializedName("album_id") val albumId: String?,
    @SerializedName("duration_ms") val durationMs: Long?,
    @SerializedName("cover") val cover: String?,
    @SerializedName("covers") val covers: CoverSet?,
    @SerializedName("source") val source: String?,
    @SerializedName("source_id") val sourceId: String?,
    @SerializedName("playback_url") val playbackUrl: String?,
    @SerializedName("explicit") val explicit: Boolean?
)

data class TrackArtist(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String?
)

data class CoverSet(
    @SerializedName("small") val small: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("large") val large: String?,
    @SerializedName("default") val default: String?
) {
    fun best(): String? = large ?: medium ?: small ?: default
}

data class SearchResponse(
    @SerializedName("tracks") val tracks: List<Track>?,
    @SerializedName("artists") val artists: List<Artist>?,
    @SerializedName("albums") val albums: List<Album>?,
    @SerializedName("total") val total: Int?
)

data class Artist(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String?,
    @SerializedName("album_count") val albumCount: Int?,
    @SerializedName("track_count") val trackCount: Int?
)

data class Album(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String?,
    @SerializedName("artist_id") val artistId: String?,
    @SerializedName("cover") val cover: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("track_count") val trackCount: Int?,
    @SerializedName("tracks") val tracks: List<Track>?
)

data class PlaybackUrl(
    @SerializedName("url") val url: String,
    @SerializedName("duration_ms") val durationMs: Long?,
    @SerializedName("format") val format: String?
)
