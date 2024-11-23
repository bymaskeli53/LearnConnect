package com.gundogar.learnconnect

class FakeAuthRepository : AuthRepository {
    private var isLoggedIn = false
    private var currentUserId: String? = null
    private val users = mutableMapOf<String, String>() // email to password map

    override fun isUserLoggedIn(): Boolean = isLoggedIn

    override suspend fun signUp(email: String, password: String): AuthResult<String> {
        return if (users.containsKey(email)) {
            AuthResult.Error(Exception("User already exists"))
        } else {
            val userId = "user_${users.size + 1}"
            users[email] = password
            currentUserId = userId
            isLoggedIn = true
            AuthResult.Success(userId)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<String> {
        return if (users[email] == password) {
            val userId = "user_${users.keys.indexOf(email) + 1}"
            currentUserId = userId
            isLoggedIn = true
            AuthResult.Success(userId)
        } else {
            AuthResult.Error(Exception("Invalid email or password"))
        }
    }

    override fun signOut() {
        isLoggedIn = false
        currentUserId = null
    }

    // Test helper functions
    fun getRegisteredUsers() = users.toMap()
    fun getCurrentUserId() = currentUserId
}