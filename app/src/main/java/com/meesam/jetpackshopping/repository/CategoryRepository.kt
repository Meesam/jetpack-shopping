package com.meesam.jetpackshopping.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.meesam.jetpackshopping.model.Category
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        private const val CATEGORY_COLLECTION = "categories"
    }

    suspend fun getAllCategory(): List<Category>{
        return try {
            val result = firestore.collection(CATEGORY_COLLECTION)
                .orderBy("name")
                .get()
                .await()
            result.toObjects(Category::class.java)
        }catch (e: Exception){
            emptyList()
        }
    }

}