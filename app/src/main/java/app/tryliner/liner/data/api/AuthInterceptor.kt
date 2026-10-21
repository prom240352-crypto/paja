package app.tryliner.liner.data.api

import app.tryliner.liner.data.model.AuthTokens
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    
    private var accessToken: String? = null
    
    fun setAccessToken(token: String?) {
        accessToken = token
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        
        accessToken?.let { token ->
            builder.addHeader("Authorization", "Bearer $token")
        }
        
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        builder.addHeader("User-Agent", "Liner-Android/1.0.0")
        
        return chain.proceed(builder.build())
    }
}
