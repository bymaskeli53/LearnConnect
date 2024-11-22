package com.gundogar.learnconnect

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: FirebaseAuth) {

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun signUp(email: String, password: String): AuthResult<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): AuthResult<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    fun signOut() = auth.signOut()
}