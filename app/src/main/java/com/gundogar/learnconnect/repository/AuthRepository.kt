package com.gundogar.learnconnect.repository

import com.gundogar.learnconnect.util.AuthResult

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun signUp(email: String, password: String): AuthResult<String>
    suspend fun signIn(email: String, password: String): AuthResult<String>
    fun signOut()
    fun getCurrentUserEmail(): String
}