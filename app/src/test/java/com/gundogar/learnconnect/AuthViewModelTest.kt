package com.gundogar.learnconnect

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test



@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private lateinit var viewModel: AuthViewModel
    private lateinit var fakeAuthRepository: FakeAuthRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeAuthRepository = FakeAuthRepository()
        viewModel = AuthViewModel(fakeAuthRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when email is empty, should set validation error`() = runTest {
        // When
        viewModel.signUp("", "password123")

        // Then
        viewModel.validationError.test {
            assertEquals("Geçerli bir email girin.", awaitItem())
        }
        viewModel.authResult.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `when email is invalid, should set validation error`() = runTest {
        // When
        viewModel.signUp("invalidemail", "password123")

        // Then
        viewModel.validationError.test {
            assertEquals("Geçerli bir email girin.", awaitItem())
        }
        viewModel.authResult.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `when password is too short, should set validation error`() = runTest {
        // When
        viewModel.signUp("test@test.com", "12345")

        // Then
        viewModel.validationError.test {
            assertEquals("Şifre en az 6 karakter olmalı.", awaitItem())
        }
        viewModel.authResult.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `when signup with valid credentials, should return success`() = runTest {
        // When
        viewModel.signUp("test@test.com", "password123")
        advanceUntilIdle() // Wait for coroutines to complete

        // Then
        viewModel.authResult.test {
            val result = awaitItem()
            assertTrue(result is AuthResult.Success)
            assertEquals("user_1", (result as AuthResult.Success).data)
        }
        viewModel.validationError.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `when signup with existing email, should return error`() = runTest {
        // Given
        viewModel.signUp("test@test.com", "password123")
        advanceUntilIdle()

        // When
        viewModel.signUp("test@test.com", "password456")
        advanceUntilIdle()

        // Then
        viewModel.authResult.test {
            val result = awaitItem()
            assertTrue(result is AuthResult.Error)
            assertEquals("User already exists", (result as AuthResult.Error).exception.message)
        }
    }

    @Test
    fun `when signin with valid credentials, should return success`() = runTest {
        // Given
        viewModel.signUp("test@test.com", "password123")
        advanceUntilIdle()

        // When
        viewModel.signIn("test@test.com", "password123")
        advanceUntilIdle()

        // Then
        viewModel.authResult.test {
            val result = awaitItem()
            assertTrue(result is AuthResult.Success)
            assertEquals("user_1", (result as AuthResult.Success).data)
        }
    }

    @Test
    fun `when signin with invalid credentials, should return error`() = runTest {
        // Given
        viewModel.signUp("test@test.com", "password123")
        advanceUntilIdle()

        // When
        viewModel.signIn("test@test.com", "wrongpassword")
        advanceUntilIdle()

        // Then
        viewModel.authResult.test {
            val result = awaitItem()
            assertTrue(result is AuthResult.Error)
            assertEquals("Invalid email or password", (result as AuthResult.Error).exception.message)
        }
    }
}