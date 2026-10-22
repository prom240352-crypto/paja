package app.tryliner.liner.data.api

import app.tryliner.liner.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface LinerApi {
    
    // Authentication
    @POST("v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("v1/auth/refresh")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<AuthTokens>
    
    @GET("v1/me")
    suspend fun getCurrentUser(): Response<User>
    
    // Search
    @GET("v1/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String = "all",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<SearchResponse>
    
    // Tracks
    @GET("v1/tracks/{id}")
    suspend fun getTrack(@Path("id") trackId: String): Response<Track>
    
    @GET("v1/tracks/{id}/playback")
    suspend fun getPlaybackUrl(@Path("id") trackId: String): Response<PlaybackUrl>
    
    @GET("v1/tracks/{id}/lyrics")
    suspend fun getLyrics(@Path("id") trackId: String): Response<Lyrics>
    
    // Artists
    @GET("v1/artists/{id}")
    suspend fun getArtist(@Path("id") artistId: String): Response<Artist>
    
    @GET("v1/artists/{id}/tracks")
    suspend fun getArtistTracks(
        @Path("id") artistId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<List<Track>>
    
    @GET("v1/artists/{id}/albums")
    suspend fun getArtistAlbums(@Path("id") artistId: String): Response<List<Album>>
    
    // Albums
    @GET("v1/albums/{id}")
    suspend fun getAlbum(@Path("id") albumId: String): Response<Album>
    
    @GET("v1/albums/{id}/tracks")
    suspend fun getAlbumTracks(@Path("id") albumId: String): Response<List<Track>>
    
    // Library
    @GET("v1/me/library/tracks")
    suspend fun getLibraryTracks(
        @Query("limit") limit: Int = 50,
        @Query("cursor") cursor: String? = null
    ): Response<LibraryTracksResponse>
    
    @POST("v1/me/library/tracks")
    suspend fun addToLibrary(@Body body: Map<String, String>): Response<Unit>
    
    @DELETE("v1/me/library/tracks")
    suspend fun removeFromLibrary(@Body body: Map<String, String>): Response<Unit>
    
    @GET("v1/me/history")
    suspend fun getRecentPlays(
        @Query("limit") limit: Int = 50,
        @Query("cursor") cursor: String? = null
    ): Response<PlaybackHistoryResponse>
    
    // Home Screen
    @GET("v1/me/recommendations/daily-mixes")
    suspend fun getDailyMixes(): Response<DailyMixesResponse>
    
    @GET("v1/explore/popular")
    suspend fun getPopularTracks(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PopularTracksResponse>
    
    // Playlists
    @GET("v1/playlists")
    suspend fun getPlaylists(): Response<List<Playlist>>
    
    @GET("v1/playlists/{id}")
    suspend fun getPlaylist(@Path("id") playlistId: String): Response<Playlist>
    
    @POST("v1/playlists")
    suspend fun createPlaylist(@Body playlist: Map<String, String>): Response<Playlist>
    
    @POST("v1/playlists/{id}/tracks")
    suspend fun addTrackToPlaylist(
        @Path("id") playlistId: String,
        @Body body: Map<String, String>
    ): Response<Unit>
    
    // Playlist Import
    @POST("v1/import/spotify")
    suspend fun importSpotifyPlaylist(@Body body: Map<String, String>): Response<Playlist>
    
    @POST("v1/import/youtube")
    suspend fun importYouTubePlaylist(@Body body: Map<String, String>): Response<Playlist>
    
    @POST("v1/import/soundcloud")
    suspend fun importSoundCloudPlaylist(@Body body: Map<String, String>): Response<Playlist>
}
