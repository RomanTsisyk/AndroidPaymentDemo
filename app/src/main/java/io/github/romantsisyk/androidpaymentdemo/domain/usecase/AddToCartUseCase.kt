package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(product: Product, quantity: Int = 1) {
        cartRepository.addToCart(product, quantity)
    }
}