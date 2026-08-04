package com.example.montasch

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object AdminPinVerifier {
    fun matches(pin: String, salt: String, expectedHash: String): Boolean {
        val actualHash = MessageDigest.getInstance("SHA-256")
            .digest((salt + pin).toByteArray(StandardCharsets.UTF_8))
            .joinToString(separator = "") { byte -> "%02x".format(byte) }

        return MessageDigest.isEqual(
            actualHash.toByteArray(StandardCharsets.US_ASCII),
            expectedHash.lowercase().toByteArray(StandardCharsets.US_ASCII)
        )
    }
}
