package io.github.romantsisyk.androidpaymentdemo.data.repository

import io.github.romantsisyk.androidpaymentdemo.domain.model.Cart
import io.github.romantsisyk.androidpaymentdemo.domain.model.CartItem
import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor() : CartRepository {

    private val _cart = MutableStateFlow(Cart())

    override fun getCart(): Flow<Cart> = _cart

    override suspend fun addToCart(product: Product, quantity: Int) {
        val currentCart = _cart.value
        val currentItems = currentCart.items.toMutableList()

        val existingItemIndex = currentItems.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex >= 0) {
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] = existingItem.copy(
                quantity = existingItem.quantity + quantity
            )
        } else {
            currentItems.add(CartItem(product, quantity))
        }

        _cart.value = Cart(currentItems)
    }

    override suspend fun removeFromCart(productId: String, quantity: Int) {
        val currentCart = _cart.value
        val currentItems = currentCart.items.toMutableList()

        val existingItemIndex = currentItems.indexOfFirst { it.product.id == productId }

        if (existingItemIndex >= 0) {
            val existingItem = currentItems[existingItemIndex]
            val newQuantity = existingItem.quantity - quantity

            if (newQuantity <= 0) {
                currentItems.removeAt(existingItemIndex)
            } else {
                currentItems[existingItemIndex] = existingItem.copy(quantity = newQuantity)
            }

            _cart.value = Cart(currentItems)
        }
    }

    override suspend fun updateQuantity(productId: String, quantity: Int) {
        val currentCart = _cart.value
        val currentItems = currentCart.items.toMutableList()

        val existingItemIndex = currentItems.indexOfFirst { it.product.id == productId }

        if (existingItemIndex >= 0) {
            if (quantity <= 0) {
                currentItems.removeAt(existingItemIndex)
            } else {
                val existingItem = currentItems[existingItemIndex]
                currentItems[existingItemIndex] = existingItem.copy(quantity = quantity)
            }

            _cart.value = Cart(currentItems)
        }
    }

    override suspend fun clearCart() {
        _cart.value = Cart()
    }
}