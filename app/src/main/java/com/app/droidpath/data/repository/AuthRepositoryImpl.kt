package com.app.droidpath.data.repository

import com.app.droidpath.utils.UserPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val userPreferencesManager: UserPreferencesManager) :
    AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(1000)
        return if (email == "test@test.com" && password == "123456") {
            userPreferencesManager.saveSession(email)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override fun isLoggedIn(): Flow<Boolean> =
        userPreferencesManager.isLoggedIn
}