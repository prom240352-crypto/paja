package app.tryliner.liner.util

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object RequestSigner {
    
    // API signing key components (from signer/src/crypto.rs)
    private val OBF_API_SEED = byteArrayOf(
        0x55, 0xc3.toByte(), 0x5c, 0xaa.toByte(), 0x3f, 0x3c, 0x32, 0x6d,
        0xaa.toByte(), 0x63, 0x0f, 0x90.toByte(), 0x39, 0x5d, 0x66, 0x36
    )
    
    private val OBF_API_KEY = byteArrayOf(
        0x24, 0x99, 0x56, 0x2b, 0x80.toByte(), 0x20, 0xc1.toByte(), 0xb7.toByte(),
        0xf6.toByte(), 0x7d, 0x74, 0x91.toByte(), 0xe4.toByte(), 0xc3.toByte(), 0x7f, 0x67,
        0x1a, 0x0e, 0x8a, 0xf3.toByte(), 0x7f, 0x50, 0xaf, 0x2e,
        0x05, 0xd5, 0xa7, 0xb0.toByte(), 0xbe.toByte(), 0x6e, 0x31, 0x11,
        0x40, 0xf3.toByte(), 0x42, 0xad.toByte(), 0x31, 0xf6.toByte(), 0xcb.toByte(), 0xea.toByte(),
        0xb5.toByte(), 0xba.toByte(), 0xdd.toByte(), 0xd0.toByte(), 0x8e, 0x56, 0x55, 0x8c,
        0xb4.toByte(), 0xe5.toByte(), 0x1d, 0x82.toByte(), 0x69, 0x0c, 0x3f, 0xb2.toByte(),
        0x30, 0x82.toByte(), 0xa7.toByte(), 0xf4.toByte(), 0x9b.toByte(), 0xba.toByte(), 0x9b.toByte(), 0x70
    )
    
    private val API_KEY_CHECKSUM = byteArrayOf(
        0xe2.toByte(), 0x09, 0x02, 0x55, 0x83.toByte(), 0xee.toByte(), 0xf0.toByte(), 0x07
    )
    
    private const val KEY_LEN = 64
    
    // Server time offset for clock sync
    private var serverTimeOffsetMs: Long = 0
    
    /**
     * Generate keystream: SHA256(seed || [i]) blocks concatenated
     */
    private fun generateKeystream(seed: ByteArray, length: Int): ByteArray {
        val out = ByteArray(length + 32)
        var outLen = 0
        var i: Byte = 0
        
        while (outLen < length) {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update(seed)
            digest.update(byteArrayOf(i))
            val hash = digest.digest()
            System.arraycopy(hash, 0, out, outLen, hash.size)
            outLen += hash.size
            i = (i.toInt() + 1).toByte()
        }
        
        return out.copyOf(length)
    }
    
    /**
     * Deobfuscate the API signing key
     */
    private fun deobfuscateApiKey(): ByteArray {
        val keystream = generateKeystream(OBF_API_SEED, KEY_LEN)
        val key = ByteArray(KEY_LEN)
        
        for (idx in 0 until KEY_LEN) {
            key[idx] = (OBF_API_KEY[idx].toInt() xor keystream[idx].toInt()).toByte()
        }
        
        // Verify checksum
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(key)
        val hash = digest.digest()
        
        for (i in 0 until 8) {
            if (hash[i] != API_KEY_CHECKSUM[i]) {
                throw IllegalStateException("API key checksum mismatch - deobfuscation failed")
            }
        }
        
        return key
    }
    
    /**
     * Get adjusted timestamp (synced with server)
     */
    fun getAdjustedTimestamp(): Long {
        return (System.currentTimeMillis() + serverTimeOffsetMs) / 1000
    }
    
    /**
     * Sync server time from Date header
     */
    fun syncServerTime(dateHeader: String) {
        try {
            val serverMs = java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", java.util.Locale.US)
                .parse(dateHeader)?.time ?: return
            
            serverTimeOffsetMs = serverMs - System.currentTimeMillis()
        } catch (e: Exception) {
            // Ignore parse errors
        }
    }
    
    /**
     * Generate random nonce (16 hex characters = 8 bytes)
     */
    private fun generateNonce(): String {
        val bytes = ByteArray(8)
        java.security.SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }
    
    /**
     * Compute SHA-256 hex string
     */
    private fun sha256Hex(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(data)
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
    
    /**
     * Sign an API request
     * 
     * @param method HTTP method (GET, POST, etc.)
     * @param path Request path (without query string)
     * @param body Request body (empty string if none)
     * @return Triple of (signature, timestamp, nonce)
     */
    fun signRequest(method: String, path: String, body: String = ""): Triple<String, Long, String> {
        val apiKey = deobfuscateApiKey()
        val timestamp = getAdjustedTimestamp()
        val nonce = generateNonce()
        
        // Create canonical string
        val cleanPath = path.split("?")[0].trim()
        val bodySha256 = sha256Hex(body.toByteArray(Charsets.UTF_8))
        val canonical = "${method.uppercase().trim()}\n$cleanPath\n$timestamp\n$nonce\n$bodySha256"
        
        // Compute HMAC-SHA256
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(apiKey, "HmacSHA256"))
        val signature = mac.doFinal(canonical.toByteArray(Charsets.UTF_8))
        
        // Encode as base64url (no padding)
        val signatureBase64 = Base64.encodeToString(signature, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
        
        return Triple(signatureBase64, timestamp, nonce)
    }
}
