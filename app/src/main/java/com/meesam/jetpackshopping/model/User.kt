package com.meesam.jetpackshopping.model

data class User(
    val id: String = "",
    val name : String = "",
    val userid: String = "",
    val email: String ="",
    val role: String ="",
    val timestamp: com.google.firebase.Timestamp? = null,

)
