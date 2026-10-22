package app.tryliner.liner.data.api

import app.tryliner.liner.data.model.*
import com.google.gson.*
import java.lang.reflect.Type

class SearchItemDeserializer : JsonDeserializer<SearchItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SearchItem {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString ?: "track"
        
        return when (type) {
            "track" -> {
                val id = jsonObject.get("id").asString
                val title = jsonObject.get("title").asString
                val artists = context.deserialize<List<TrackArtist>>(
                    jsonObject.get("artists"), 
                    object : com.google.gson.reflect.TypeToken<List<TrackArtist>>() {}.type
                )
                val album = if (jsonObject.has("album") && !jsonObject.get("album").isJsonNull) {
                    context.deserialize<TrackAlbum>(jsonObject.get("album"), TrackAlbum::class.java)
                } else null
                val durationMs = jsonObject.get("durationMs").asLong
                val cover = if (jsonObject.has("cover") && !jsonObject.get("cover").isJsonNull) {
                    context.deserialize<Cover>(jsonObject.get("cover"), Cover::class.java)
                } else null
                val explicit = if (jsonObject.has("explicit")) jsonObject.get("explicit").asBoolean else null
                
                SearchItem.TrackItem(
                    id = id,
                    title = title,
                    artists = artists ?: emptyList(),
                    album = album,
                    durationMs = durationMs,
                    cover = cover,
                    explicit = explicit
                )
            }
            "artist" -> {
                val id = jsonObject.get("id").asString
                val name = jsonObject.get("name").asString
                val cover = if (jsonObject.has("cover") && !jsonObject.get("cover").isJsonNull) {
                    context.deserialize<Cover>(jsonObject.get("cover"), Cover::class.java)
                } else null
                
                SearchItem.ArtistItem(id = id, name = name, cover = cover)
            }
            "album" -> {
                val id = jsonObject.get("id").asString
                val title = jsonObject.get("title").asString
                val artists = if (jsonObject.has("artists") && !jsonObject.get("artists").isJsonNull) {
                    context.deserialize<List<TrackArtist>>(
                        jsonObject.get("artists"),
                        object : com.google.gson.reflect.TypeToken<List<TrackArtist>>() {}.type
                    )
                } else emptyList()
                val year = if (jsonObject.has("year") && !jsonObject.get("year").isJsonNull) {
                    jsonObject.get("year").asInt
                } else null
                val cover = if (jsonObject.has("cover") && !jsonObject.get("cover").isJsonNull) {
                    context.deserialize<Cover>(jsonObject.get("cover"), Cover::class.java)
                } else null
                
                SearchItem.AlbumItem(id = id, title = title, artists = artists ?: emptyList(), year = year, cover = cover)
            }
            "playlist" -> {
                val id = jsonObject.get("id").asString
                val title = jsonObject.get("title").asString
                val author = if (jsonObject.has("author") && !jsonObject.get("author").isJsonNull) {
                    jsonObject.get("author").asString
                } else null
                val cover = if (jsonObject.has("cover") && !jsonObject.get("cover").isJsonNull) {
                    context.deserialize<Cover>(jsonObject.get("cover"), Cover::class.java)
                } else null
                
                SearchItem.PlaylistItem(id = id, title = title, author = author, cover = cover)
            }
            else -> throw JsonParseException("Unknown search item type: $type")
        }
    }
}
