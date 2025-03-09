package io.github.romantsisyk.androidpaymentdemo.domain.repository

import io.github.romantsisyk.androidpaymentdemo.domain.model.Cart
import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<Cart>
    suspend fun addToCart(product: Product, quantity: Int = 1)
    suspend fun removeFromCart(productId: String, quantity: Int = 1)
    suspend fun updateQuantity(productId: String, quantity: Int)
    suspend fun clearCart()
}