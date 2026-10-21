package app.tryliner.liner.data.api

import app.tryliner.liner.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface LinerApi {
    
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<AuthTokens>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<User>
    
    // Search
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String = "all",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<SearchResponse>
    
    // Tracks
    @GET("tracks/{id}")
    suspend fun getTrack(@Path("id") trackId: String): Response<Track>
    
    @GET("tracks/{id}/playback")
    suspend fun getPlaybackUrl(@Path("id") trackId: String): Response<PlaybackUrl>
    
    @GET("tracks/{id}/lyrics")
    suspend fun getLyrics(@Path("id") trackId: String): Response<Lyrics>
    
    // Artists
    @GET("artists/{id}")
    suspend fun getArtist(@Path("id") artistId: String): Response<Artist>
    
    @GET("artists/{id}/tracks")
    suspend fun getArtistTracks(
        @Path("id") artistId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<List<Track>>
    
    @GET("artists/{id}/albums")
    suspend fun getArtistAlbums(@Path("id") artistId: String): Response<List<Album>>
    
    // Albums
    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") albumId: String): Response<Album>
    
    @GET("albums/{id}/tracks")
    suspend fun getAlbumTracks(@Path("id") albumId: String): Response<List<Track>>
    
    // Library
    @GET("library/tracks")
    suspend fun getLibraryTracks(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<List<Track>>
    
    @POST("library/tracks/{id}")
    suspend fun addToLibrary(@Path("id") trackId: String): Response<Unit>
    
    @DELETE("library/tracks/{id}")
    suspend fun removeFromLibrary(@Path("id") trackId: String): Response<Unit>
    
    @GET("library/recent")
    suspend fun getRecentPlays(
        @Query("limit") limit: Int = 50
    ): Response<List<Track>>
    
    // Playlists
    @GET("playlists")
    suspend fun getPlaylists(): Response<List<Playlist>>
    
    @GET("playlists/{id}")
    suspend fun getPlaylist(@Path("id") playlistId: String): Response<Playlist>
    
    @POST("playlists")
    suspend fun createPlaylist(@Body playlist: Map<String, String>): Response<Playlist>
    
    @POST("playlists/{id}/tracks")
    suspend fun addTrackToPlaylist(
        @Path("id") playlistId: String,
        @Body body: Map<String, String>
    ): Response<Unit>
    
    // Playlist Import
    @POST("import/spotify")
    suspend fun importSpotifyPlaylist(@Body body: Map<String, String>): Response<Playlist>
    
    @POST("import/youtube")
    suspend fun importYouTubePlaylist(@Body body: Map<String, String>): Response<Playlist>
    
    @POST("import/soundcloud")
    suspend fun importSoundCloudPlaylist(@Body body: Map<String, String>): Response<Playlist>
}
