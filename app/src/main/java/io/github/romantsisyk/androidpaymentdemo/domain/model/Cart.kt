package io.github.romantsisyk.androidpaymentdemo.domain.model

data class Cart(
    val items: List<CartItem> = emptyList()
) {
    val totalPrice: Long = items.sumOf { it.product.price * it.quantity }

    val isEmpty: Boolean = items.isEmpty()

    val totalItems: Int = items.sumOf { it.quantity }
}