package app.tryliner.liner.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("created_at") val createdAt: String?
)

data class AuthTokens(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessTokenExpiresAt") val accessTokenExpiresAt: String,
    @SerializedName("user") val user: User
)

data class AuthResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessTokenExpiresAt") val accessTokenExpiresAt: String,
    @SerializedName("user") val user: User
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("displayName") val displayName: String = username
)
