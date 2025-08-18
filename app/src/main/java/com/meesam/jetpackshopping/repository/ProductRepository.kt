package com.meesam.jetpackshopping.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.meesam.jetpackshopping.model.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        private const val PRODUCT_COLLECTION ="products"
    }

    suspend fun getAllProducts():List<Product>{
        return try {
            val result = firestore.collection(PRODUCT_COLLECTION)
                .orderBy("title")
                .get()
                .await()
            result.documents.mapNotNull { document ->
                val product = document.toObject(Product::class.java)
                product?.apply { id = document.id }
            }
        }catch (e: Exception){
            emptyList()
        }
    }

    suspend fun getProductById(id: String): Product?{
        return try {
            val result = firestore.collection(PRODUCT_COLLECTION)
                .document(id)
                .get()
                .await()
            result.toObject(Product::class.java)
        }catch (ex: Exception){
            return null
        }
    }

}