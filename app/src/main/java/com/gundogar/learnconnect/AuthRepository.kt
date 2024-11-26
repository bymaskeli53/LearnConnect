package com.gundogar.learnconnect

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun signUp(email: String, password: String): AuthResult<String>
    suspend fun signIn(email: String, password: String): AuthResult<String>
    fun signOut()
    fun getCurrentUserEmail(): String
}