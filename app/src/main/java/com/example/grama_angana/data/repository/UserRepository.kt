package com.example.grama_angana.data.repository

import com.example.grama_angana.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // Hardcoded user ID for testing until Firebase Auth is fully configured
    private val testUid = "test_user_123"

    suspend fun getUserProfile(): Result<User?> {
        return try {
            val document = firestore.collection("users")
                .document(testUid)
                .get()
                .await()

            if (document.exists()) {
                Result.success(document.toObject(User::class.java))
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveUserProfile(user: User): Result<Unit> {
        return try {
            val userWithId = user.copy(uid = testUid)
            firestore.collection("users")
                .document(testUid)
                .set(userWithId)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}