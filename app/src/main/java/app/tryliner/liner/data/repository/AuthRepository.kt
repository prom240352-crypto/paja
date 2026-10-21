package app.tryliner.liner.data.repository

import app.tryliner.liner.data.api.AuthInterceptor
import app.tryliner.liner.data.api.LinerApi
import app.tryliner.liner.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: LinerApi,
    private val authInterceptor: AuthInterceptor
) {
    private var currentUser: User? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null
    
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                currentUser = authResponse.user
                accessToken = authResponse.tokens.accessToken
                refreshToken = authResponse.tokens.refreshToken
                authInterceptor.setAccessToken(accessToken)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Login failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(username: String, email: String, password: String): Result<AuthResponse> {
        return try {
            val response = api.register(RegisterRequest(username, email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                currentUser = authResponse.user
                accessToken = authResponse.tokens.accessToken
                refreshToken = authResponse.tokens.refreshToken
                authInterceptor.setAccessToken(accessToken)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUser(): Result<User> {
        return try {
            val response = api.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                currentUser = response.body()!!
                Result.success(currentUser!!)
            } else {
                Result.failure(Exception("Failed to get user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun isLoggedIn(): Boolean = accessToken != null
    
    fun getUser(): User? = currentUser
    
    fun logout() {
        currentUser = null
        accessToken = null
        refreshToken = null
        authInterceptor.setAccessToken(null)
    }
}
