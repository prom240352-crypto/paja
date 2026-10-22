package app.tryliner.liner.data.repository

import app.tryliner.liner.data.api.LinerApi
import app.tryliner.liner.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val api: LinerApi
) {
    suspend fun search(query: String, type: String = "all"): Result<SearchResponse> {
        return try {
            val response = api.search(query, type)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Search failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTrack(trackId: String): Result<Track> {
        return try {
            val response = api.getTrack(trackId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get track: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPlaybackUrl(trackId: String): Result<PlaybackUrl> {
        return try {
            val response = api.getPlaybackUrl(trackId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get playback URL: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLyrics(trackId: String): Result<Lyrics> {
        return try {
            val response = api.getLyrics(trackId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get lyrics: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getArtist(artistId: String): Result<Artist> {
        return try {
            val response = api.getArtist(artistId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get artist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getArtistTracks(artistId: String): Result<List<Track>> {
        return try {
            val response = api.getArtistTracks(artistId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get artist tracks: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getAlbum(albumId: String): Result<Album> {
        return try {
            val response = api.getAlbum(albumId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get album: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLibraryTracks(): Result<LibraryTracksResponse> {
        return try {
            val response = api.getLibraryTracks()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get library: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRecentPlays(): Result<PlaybackHistoryResponse> {
        return try {
            val response = api.getRecentPlays()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get recent plays: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDailyMixes(): Result<DailyMixesResponse> {
        return try {
            val response = api.getDailyMixes()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get daily mixes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addToLibrary(trackId: String): Result<Unit> {
        return try {
            val response = api.addToLibrary(mapOf("trackId" to trackId))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to add to library: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
