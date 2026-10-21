package app.tryliner.liner.data.api

import app.tryliner.liner.data.model.AuthTokens
import app.tryliner.liner.util.RequestSigner
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
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
        
        // Add authorization header if we have a token
        accessToken?.let { token ->
            builder.addHeader("Authorization", "Bearer $token")
        }
        
        // Add request signing headers
        try {
            val method = request.method
            val path = request.url.encodedPath
            val fullUrl = request.url.toString()
            android.util.Log.d("AuthInterceptor", "Signing request: $method $fullUrl (path: $path)")
            
            val body = request.body?.let { reqBody ->
                val buffer = Buffer()
                reqBody.writeTo(buffer)
                buffer.readUtf8()
            } ?: ""
            
            val (signature, timestamp, nonce) = RequestSigner.signRequest(method, path, body)
            
            builder.addHeader("x-liner-signature", signature)
            builder.addHeader("x-liner-timestamp", timestamp.toString())
            builder.addHeader("x-liner-nonce", nonce)
            android.util.Log.d("AuthInterceptor", "Added signing headers for $fullUrl")
        } catch (e: Exception) {
            // If signing fails, log the error
            android.util.Log.e("AuthInterceptor", "Failed to sign request: ${e.message}", e)
        }
        
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        builder.addHeader("User-Agent", "Liner-Android/2.0.1")
        
        val response = chain.proceed(builder.build())
        
        // Sync server time from Date header
        response.header("Date")?.let { dateHeader ->
            RequestSigner.syncServerTime(dateHeader)
        }
        
        return response
    }
}
