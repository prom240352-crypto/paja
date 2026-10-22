package app.tryliner.liner.data.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("artists") val artists: List<TrackArtist>,
    @SerializedName("album") val album: TrackAlbum?,
    @SerializedName("durationMs") val durationMs: Long,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("explicit") val explicit: Boolean?
) {
    val artistName: String
        get() = artists.firstOrNull()?.name ?: "Unknown Artist"
}

data class TrackArtist(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

data class TrackAlbum(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)

data class Cover(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("fallbackUrl") val fallbackUrl: String?
)

data class SearchResponse(
    @SerializedName("items") val items: List<SearchItem>
)

data class SearchItem(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("artists") val artists: List<TrackArtist>?,
    @SerializedName("album") val album: TrackAlbum?,
    @SerializedName("durationMs") val durationMs: Long?,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("explicit") val explicit: Boolean?,
    @SerializedName("year") val year: Int?,
    @SerializedName("author") val author: String?
) {
    val displayName: String
        get() = title ?: name ?: "Unknown"
    
    val artistName: String
        get() = artists?.firstOrNull()?.name ?: author ?: "Unknown Artist"
    
    val track: Track?
        get() = if (type == "track" && title != null && artists != null && durationMs != null) {
            Track(id, title, artists, album, durationMs, cover, explicit)
        } else null
}

data class Artist(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val cover: Cover?
)

data class Album(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("artists") val artists: List<TrackArtist>?,
    @SerializedName("year") val year: Int?,
    @SerializedName("cover") val cover: Cover?
)

data class PlaybackUrl(
    @SerializedName("url") val url: String,
    @SerializedName("durationMs") val durationMs: Long?,
    @SerializedName("format") val format: String?
)

data class LibraryTracksResponse(
    @SerializedName("items") val items: List<Track>,
    @SerializedName("nextCursor") val nextCursor: String?
)

data class PlaybackHistoryItem(
    @SerializedName("id") val id: String,
    @SerializedName("track") val track: Track,
    @SerializedName("listenedAt") val listenedAt: String
)

data class PlaybackHistoryResponse(
    @SerializedName("items") val items: List<PlaybackHistoryItem>,
    @SerializedName("nextCursor") val nextCursor: String?
)
