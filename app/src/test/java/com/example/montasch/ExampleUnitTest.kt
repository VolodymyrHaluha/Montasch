package com.example.montasch

import org.junit.Test

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val salt = "frop-kiosk-v1:"
    private val pinHash = "cbb87b96924eac8a0875a5fd09260bb435792a9e5a59a8454645c4ea68f77eb9"

    @Test
    fun acceptsConfiguredAdminPin() {
        assertTrue(AdminPinVerifier.matches("12345", salt, pinHash))
    }

    @Test
    fun rejectsIncorrectAdminPin() {
        assertFalse(AdminPinVerifier.matches("1234", salt, pinHash))
        assertFalse(AdminPinVerifier.matches("", salt, pinHash))
    }
}
