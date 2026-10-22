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

sealed class SearchItem {
    abstract val id: String
    abstract val type: String
    
    data class TrackItem(
        override val id: String,
        override val type: String = "track",
        val title: String,
        val artists: List<TrackArtist>,
        val album: TrackAlbum?,
        val durationMs: Long,
        val cover: Cover?,
        val explicit: Boolean?
    ) : SearchItem() {
        val track: Track
            get() = Track(id, title, artists, album, durationMs, cover, explicit)
    }
    
    data class ArtistItem(
        override val id: String,
        override val type: String = "artist",
        val name: String,
        val cover: Cover?
    ) : SearchItem()
    
    data class AlbumItem(
        override val id: String,
        override val type: String = "album",
        val title: String,
        val artists: List<TrackArtist>,
        val year: Int?,
        val cover: Cover?
    ) : SearchItem()
    
    data class PlaylistItem(
        override val id: String,
        override val type: String = "playlist",
        val title: String,
        val author: String?,
        val cover: Cover?
    ) : SearchItem()
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
