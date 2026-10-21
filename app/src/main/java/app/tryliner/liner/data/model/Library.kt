package app.tryliner.liner.data.model

import com.google.gson.annotations.SerializedName

data class Lyrics(
    @SerializedName("track_id") val trackId: String,
    @SerializedName("synced") val synced: Boolean?,
    @SerializedName("lines") val lines: List<LyricsLine>?,
    @SerializedName("text") val text: String?
)

data class LyricsLine(
    @SerializedName("time_ms") val timeMs: Long,
    @SerializedName("text") val text: String,
    @SerializedName("words") val words: List<LyricsWord>?
)

data class LyricsWord(
    @SerializedName("time_ms") val timeMs: Long,
    @SerializedName("duration_ms") val durationMs: Long?,
    @SerializedName("text") val text: String
)

data class Playlist(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("cover") val cover: String?,
    @SerializedName("track_count") val trackCount: Int?,
    @SerializedName("tracks") val tracks: List<Track>?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("owner") val owner: String?
)

data class LibraryResponse(
    @SerializedName("tracks") val tracks: List<Track>?,
    @SerializedName("playlists") val playlists: List<Playlist>?,
    @SerializedName("recent") val recent: List<Track>?
)
