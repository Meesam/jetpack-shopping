package com.meesam.jetpackshopping.repository


import android.app.AuthenticationRequiredException
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String> {
        return try {
           val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.toString())
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No user found with this email."))
        }catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Invalid email or password."))
        }catch (e: FirebaseNetworkException){
            Result.failure(Exception("Network error. Please check your connection."))
        }catch (e: Exception){
            Result.failure(e)
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
            val genId = firestore.collection("users").document().id
            val newUser = User(
                id = genId,
                name = user.name,
                userid = user.userid,
                timestamp = Timestamp.now(),
                role = "user"
            )
            val userDoc = firestore.collection(USERS_COLLECTION)
                .add(newUser)
                .await()
            Result.success(userDoc.id)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun getCurrentUserDetails(): User? {
        return try {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null && firebaseUser.email != null) {
                val uid = firebaseUser.uid
                val email = firebaseUser.email.toString()
                val querySnapshot = firestore.collection(USERS_COLLECTION)
                    .whereEqualTo("userid", uid)
                    .limit(1)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents.first()
                    val user =  document.toObject(User::class.java)
                    user?.copy(email = email)
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}