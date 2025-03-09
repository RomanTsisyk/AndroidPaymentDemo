package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
}