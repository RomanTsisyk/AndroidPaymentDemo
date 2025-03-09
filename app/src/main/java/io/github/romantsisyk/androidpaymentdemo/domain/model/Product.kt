package io.github.romantsisyk.androidpaymentdemo.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Long, // In cents
    val imageUrl: String
)