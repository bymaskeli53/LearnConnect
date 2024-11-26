package com.gundogar.learnconnect

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setup() {
        fakeAuthRepository = FakeAuthRepository()
    }

    @Test
    fun `when user signs up successfully, should return success with user id`() = runTest {
        // When
        val result = fakeAuthRepository.signUp("test@test.com", "password123")

        // Then
        assertTrue(result is AuthResult.Success)
        assertEquals("user_1", (result as AuthResult.Success).data)
        assertTrue(fakeAuthRepository.isUserLoggedIn())
    }

    @Test
    fun `when user signs up with existing email, should return error`() = runTest {
        // Given
        fakeAuthRepository.signUp("test@test.com", "password123")

        // When
        val result = fakeAuthRepository.signUp("test@test.com", "password456")

        // Then
        assertTrue(result is AuthResult.Error)
        assertEquals("User already exists", (result as AuthResult.Error).exception.message)
    }

    @Test
    fun `when user logged in, mail should return correct address`() = runTest {
        // Given
        fakeAuthRepository.signUp("test@test.com", "password123")
        // When
        val result = fakeAuthRepository.getCurrentUserEmail()
        // Then
        assertEquals("test@test.com", fakeAuthRepository.getCurrentUserEmail())
    }
}