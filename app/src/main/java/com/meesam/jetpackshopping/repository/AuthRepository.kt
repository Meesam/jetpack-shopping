package com.meesam.jetpackshopping.repository


import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meesam.jetpackshopping.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    companion object {
        private const val USERS_COLLECTION = "users"
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    suspend fun addUser(user: User): Result<String> {
        return try {
            val userDoc = firestore.collection(USERS_COLLECTION)
                .add(user)
                .await()
            Result.success(userDoc.id)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

}