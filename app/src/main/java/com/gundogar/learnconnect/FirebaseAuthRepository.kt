package com.gundogar.learnconnect

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override fun isUserLoggedIn(): Boolean = auth.currentUser != null

    override suspend fun signUp(email: String, password: String): AuthResult<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override fun signOut() = auth.signOut()
    override fun getCurrentUserEmail() : String {
        return auth.currentUser?.email ?: "No email found"
    }
}